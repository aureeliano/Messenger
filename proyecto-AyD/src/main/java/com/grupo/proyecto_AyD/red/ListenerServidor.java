package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.dtos.SolicitudLlamadaDTO;
import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.GestorDeUsuarios;
import com.grupo.proyecto_AyD.negocio.InterfazGestorUsuarios;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Clase que se encarga de escuchar las peticiones de los clientes
 * Tiene similitudes con la clase Listener que sigue la arquitectura P2P
 * @see com.grupo.proyecto_AyD.red.Listener
 * Esta sigue la arquitectura cliente-servidor
 */
public class ListenerServidor {
  private ServerSocket serverSocket;
  private BufferedReader in;
  private boolean eschuchando = false;
  private final ObjectMapper mapper;
  private final Servidor servidor;

  private ConectorServidor conectorServidor;

  private ControladorServidor controladorServidor;

  @Getter
  private int puertoEscucha;

  private InterfazGestorUsuarios gestorUsuarios;

  public ListenerServidor(ControladorServidor controlador) {
    this.servidor = Servidor.getServidor();
    this.mapper = new ObjectMapper();
    this.controladorServidor = controlador;
  }


  public void init(ConectorServidor conector) {
    try {
      conectorServidor = conector;
      conectorServidor.setListenerServidor(this);

      gestorUsuarios = new GestorDeUsuarios(conectorServidor, controladorServidor);

      try {
        // Se intenta levantar el servidor en el puerto 3001
        serverSocket = new ServerSocket(3001);
        this.puertoEscucha = 3001;
        servidor.setPuerto("3001");
        System.out.println("[SERVIDOR] Servidor principal");
      } catch (IOException e) {
        //Si falla (porque ya hay un servidor levantado en ese puerto) se levanta en el 3002
        serverSocket = new ServerSocket(3002);
        this.puertoEscucha = 3002;
        servidor.setPuerto("3002");
        System.out.println("[SERVIDOR] Servidor redundante");
      }

      this.eschuchando = true;
      escuchar();

      System.out.println("[SERVIDOR] Escuchando en puerto: " + puertoEscucha);
    } catch (Exception e) {
      System.out.println("Error al iniciar el listener: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void escuchar() {
    Thread threadListener = new Thread(() -> {
      try {
        while (eschuchando) {

          Socket socket = serverSocket.accept();
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String mensajeCrudo = in.readLine();
          System.out.println("[SERVIDOR] Mensaje recibido: " + mensajeCrudo);

          if (mensajeCrudo != null && !mensajeCrudo.contains("[SYNC]") && !mensajeCrudo.contains("[SWITCH_PRINCIPAL]")) {
            Mensaje mensaje = mapper.readValue(mensajeCrudo, Mensaje.class);
            String contenido = mensaje.getMensaje();

            if (contenido != null && contenido.contains("[CONTROL]")) {
              if (contenido.contains("[FINALIZAR_CHAT]") || contenido.contains("[CLAVE]")) {
                manejarMensaje(mensaje);
              }

              contenido = contenido.replace("[CONTROL]", "");

              if (contenido.contains("[CONEXION_CLIENTE]")){
                gestorUsuarios.manejarConexion(mensaje);
              }

              if (contenido.contains("[DESCONEXION_CLIENTE]")) {
                gestorUsuarios.manejarDesconexion(mensaje);
              }

              if (contenido.contains("[ESCUCHANDO]")) {
                if (contenido.contains("[INICIAR]")) {
                  gestorUsuarios.quitarUsuario(mensaje.getRemitente());

                  mensaje.getRemitente().setEstado(EstadoUsuario.ESCUCHANDO);
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                  controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                  gestorUsuarios.notificarConectados();

                  System.out.println("[SERVIDOR] Usuario escuchando: " + mensaje.getRemitente());
                } else {
                  gestorUsuarios.quitarUsuario(mensaje.getRemitente());

                  mensaje.getRemitente().setEstado(EstadoUsuario.INACTIVO);
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                  controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                  gestorUsuarios.notificarConectados();

                  System.out.println("[SERVIDOR] Usuario inactivo: " + mensaje.getRemitente());
                }
              }

              if (contenido.contains("[CONECTAR]")) {
                contenido = contenido.replace("[CONECTAR]", "");
                SolicitudLlamadaDTO solicitudLlamadaDTO = mapper.readValue(contenido, SolicitudLlamadaDTO.class);
                gestorUsuarios.quitarUsuario(mensaje.getRemitente());

                //Actualiza el nombre del usuario
                this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                procesarLlamada(solicitudLlamadaDTO);
              }

              if (contenido.contains("[LLAMADA]")) {
                contenido = contenido.replace("[LLAMADA]", "");

                if (contenido.contains("[ACEPTAR]")) {
                  contenido = contenido.replace("[ACEPTAR]", "");
                  SolicitudLlamadaDTO solicitudLlamadaDTO = mapper.readValue(contenido, SolicitudLlamadaDTO.class);

                  manejarAceptar(solicitudLlamadaDTO);
                }
                if (contenido.contains("[RECHAZAR]")) {
                  contenido = contenido.replace("[RECHAZAR]", "");
                  SolicitudLlamadaDTO solicitudLlamadaDTO = mapper.readValue(contenido, SolicitudLlamadaDTO.class);

                  manejarRechazar(solicitudLlamadaDTO);
                }
              }

              if (contenido.contains("[SWITCH_PRINCIPAL]")) {
                controladorServidor.manejarCambioDePuerto();
              }

              conectorServidor.sincronizar();
            } else {
              manejarMensaje(mensaje);
            }
          } else if (mensajeCrudo.contains("[SYNC]")) {
            manejarSync(mensajeCrudo);
          } else if (mensajeCrudo.contains("[SWITCH_PRINCIPAL]")) {
            controladorServidor.manejarCambioDePuerto();
          }
        }
      } catch (Exception e) {
        System.out.println("Error al escuchar: " + e.getMessage());
      }
    });

    threadListener.start();

    Thread threadSync = new Thread(() -> {
      try {
        while (eschuchando) {
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            System.out.println("Error al dormir el thread: " + e.getMessage());
          }
          if (this.servidor.getPuerto().equals("3001")) {
            conectorServidor.sincronizar();
          }
        }
      } catch (Exception e) {
        System.out.println("Error al sincronizar: " + e.getMessage());
      }
    });

    threadSync.start();
  }

  public void moverAPrincipal() {
    try {
        serverSocket.close();
    } catch (IOException e) {
        System.out.println("Error al cerrar el socket, probablemente ya este cerrado: " + e.getMessage());
    }
    init(conectorServidor);
    if (this.servidor.getPuerto().equals("3001")) {
      System.out.println("[SERVIDOR] Servidor redundante movido a principal");
    }
  }


  private void procesarLlamada(SolicitudLlamadaDTO solicitud) {
    Usuario remitente = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getSolicitante().getIp()) && u.getPuerto() == solicitud.getSolicitante().getPuerto()).findFirst().orElse(null);

    Optional<Usuario> destinatario = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getDestino().getIp()) && u.getPuerto() == solicitud.getDestino().getPuerto()).findFirst();

    if (destinatario.isEmpty()) {
      conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR] Usuario desconectado");
    }

    if (destinatario.isPresent()) {
      if (EstadoUsuario.ESCUCHANDO.equals(destinatario.get().getEstado())) {
        try {
          conectorServidor.enviarMensaje(destinatario.get(), "[CONTROL][CONECTAR][SOLICITUD]" + mapper.writeValueAsString(solicitud));
          conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR]Llamando...");
        } catch (Exception e) {
          System.out.println("Error al enviar solicitud: " + e.getMessage());
        }
      } else {
        conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR]El usuario no se encuentra escuchando");
      }
    }
  }

  private void manejarAceptar(SolicitudLlamadaDTO solicitud) {
    Usuario remitente = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getSolicitante().getIp()) && u.getPuerto() == solicitud.getSolicitante().getPuerto()).findFirst().orElse(null);
    Usuario destinatario = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getDestino().getIp()) && u.getPuerto() == solicitud.getDestino().getPuerto()).findFirst().orElse(null);

    gestorUsuarios.quitarUsuario(remitente);
    gestorUsuarios.quitarUsuario(destinatario);

    conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ACEPTAR]");

    Sesion sesion = new Sesion();
    sesion.getUsuarios().add(remitente);
    sesion.getUsuarios().add(destinatario);

    remitente.setEstado(EstadoUsuario.CONECTADO);
    destinatario.setEstado(EstadoUsuario.CONECTADO);
    servidor.getUsuariosConectados().add(remitente);
    servidor.getUsuariosConectados().add(destinatario);
    servidor.getChatsActivos().add(sesion);


    controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());
    gestorUsuarios.notificarConectados();
  }

  private void manejarRechazar(SolicitudLlamadaDTO solicitud) {
    Usuario remitente = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getSolicitante().getIp()) && u.getPuerto() == solicitud.getSolicitante().getPuerto()).findFirst().orElse(null);

    conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR]Compañero rechazó la llamada");
  }

  private void manejarMensaje(Mensaje mensaje) {
    Usuario remitente = mensaje.getRemitente();
    Sesion sesion = servidor
            .getChatsActivos()
            .stream().filter(c -> c.getUsuarios().stream().anyMatch(usuario -> usuario.getIp().equals(remitente.getIp()) && usuario.getPuerto() == remitente.getPuerto()))
            .findFirst()
            .orElse(null);

    if (sesion != null) {
      List<Usuario> destinatarios =
              sesion
                    .getUsuarios()
                    .stream()
                    .filter(u -> (!u.getIp().equals(remitente.getIp()) || u.getPuerto() != remitente.getPuerto()))
                    .toList();

      destinatarios.forEach(d -> {
        try {
          conectorServidor.reenviarMensaje(d, mensaje);
        } catch (Exception e) {
          System.out.println("Error procesando mensaje:" + e);
        }
      });

      sesion.getMensajes().add(mensaje);
    }

  }

  /**
   * Cada vez que alguien se conecta, le notificamos al resto de los usuarios conectados
   */


  private void manejarSync(String mensajeCrudo) {
    mensajeCrudo = mensajeCrudo.replace("[SYNC]", "");
    if (mensajeCrudo.contains("[USUARIOS]")) {
      mensajeCrudo = mensajeCrudo.replace("[USUARIOS]", "");

      try {
        Set<Usuario> usuarios = mapper.readValue(mensajeCrudo, new TypeReference<Set<Usuario>>() {});
        this.servidor.setUsuariosConectados(usuarios);
        controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

        System.out.println("[SERVIDOR] Usuarios sincronizados");
      } catch (JsonProcessingException e) {
        System.out.println("Error al sincronizar usuarios: " + e.getMessage());
      }
    }

    if (mensajeCrudo.contains("[CHATS]")) {
      mensajeCrudo = mensajeCrudo.replace("[CHATS]", "");

      try {
        List<Sesion> chats = mapper.readValue(mensajeCrudo, new TypeReference<List<Sesion>>() {});

        this.servidor.setChatsActivos(chats);
        System.out.println("[SERVIDOR] Chats sincronizados");
      } catch (JsonProcessingException e) {
        System.out.println("Error al sincronizar chats: " + e.getMessage());
      }
    }

    controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());
  }

}
