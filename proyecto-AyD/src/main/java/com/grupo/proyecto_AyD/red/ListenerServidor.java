package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.dtos.SolicitudLlamadaDTO;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;


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
  private ObjectMapper mapper;
  private Servidor servidor;

  private ConectorServidor conectorServidor;

  private static ListenerServidor listenerServidor;

  private ListenerServidor() {
    this.servidor = Servidor.getServidor();
    this.mapper = new ObjectMapper();
  }

  public static ListenerServidor getListenerServidor() {
    if (listenerServidor == null) {
      listenerServidor = new ListenerServidor();
    }
    return listenerServidor;
  }

  public void init() {
    try {
      conectorServidor = ConectorServidor.getConector();
      conectorServidor.init();
      serverSocket = new ServerSocket(3000);
      this.eschuchando = true;
      escuchar();

      System.out.println("[SERVIDOR] Escuchando en puerto: 3000");
    } catch (Exception e) {
      System.out.println("Error al iniciar el listener: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void escuchar() {
    Thread thread = new Thread(() -> {
      try {
        while (eschuchando) {
          System.out.println("[SERVIDOR] Esperando conexiones...");

          Socket socket = serverSocket.accept();
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String mensajeCrudo = in.readLine();
          System.out.println("[SERVIDOR] Mensaje recibido: " + mensajeCrudo);

          if (mensajeCrudo != null) {
            Mensaje mensaje = mapper.readValue(mensajeCrudo, Mensaje.class);
            String contenido = mensaje.getMensaje();

            if (contenido.contains("[CONTROL]")) {
              contenido = contenido.replace("[CONTROL]", "");

              if (contenido.contains("[CONEXION_CLIENTE]")){
                if (!this.servidor.getUsuariosConectados().contains(mensaje.getRemitente())) {
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                  ControladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                  System.out.println("[SERVIDOR] Usuario conectado: " + mensaje.getRemitente());
                  this.conectorServidor.enviarMensaje(mensaje.getRemitente(),"[CONTROL]IP:" + servidor.getIp());
                  this.conectorServidor.enviarMensaje(mensaje.getRemitente(), "[CONTROL]PUERTO:3000");
                  this.conectorServidor.enviarMensaje(mensaje.getRemitente(), "[CONTROL][CONEXION][OK]");
                }
              }

              if (contenido.contains("[DESCONEXION_CLIENTE]")) {
                Usuario remitente = mensaje.getRemitente();
                quitarUsuario(remitente);
                ControladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                System.out.println("[SERVIDOR] Usuario desconectado: " + mensaje.getRemitente());
              }

              if (contenido.contains("[ESCUCHANDO]")) {
                if (contenido.contains("[INICIAR]")) {
                  quitarUsuario(mensaje.getRemitente());

                  mensaje.getRemitente().setEstado(EstadoUsuario.ESCUCHANDO);
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                  ControladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                  System.out.println("[SERVIDOR] Usuario escuchando: " + mensaje.getRemitente());
                } else {
                  quitarUsuario(mensaje.getRemitente());

                  mensaje.getRemitente().setEstado(EstadoUsuario.INACTIVO);
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());

                  System.out.println("[SERVIDOR] Usuario inactivo: " + mensaje.getRemitente());
                }
              }

              if (contenido.contains("[CONECTAR]")) {
                contenido = contenido.replace("[CONECTAR]", "");
                SolicitudLlamadaDTO solicitudLlamadaDTO = mapper.readValue(contenido, SolicitudLlamadaDTO.class);
                procesarLlamada(solicitudLlamadaDTO);
              }
            }
          }

        }
      } catch (Exception e) {
        System.out.println("Error al escuchar: " + e.getMessage());
      }
    });

    thread.start();
  }


  private void quitarUsuario(Usuario remitente) {
    this.servidor.getUsuariosConectados().removeIf(u -> u.getIp().equals(remitente.getIp()) && u.getPuerto() == remitente.getPuerto());
  }

  private void procesarLlamada(SolicitudLlamadaDTO solicitud) {
    Usuario remitente = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getSolicitante().getIp()) && u.getPuerto() == solicitud.getSolicitante().getPuerto()).findFirst().orElse(null);
    Optional<Usuario> destinatario = this.servidor.getUsuariosConectados().stream().filter(u -> u.getIp().equals(solicitud.getDestino().getIp()) && u.getPuerto() == solicitud.getDestino().getPuerto()).findFirst();

    if (destinatario.isEmpty()) {
      conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR]Usuario desconectado");
    }

    if (destinatario.isPresent()) {
      if (EstadoUsuario.ESCUCHANDO.equals(destinatario.get().getEstado())) {
        try {
          conectorServidor.enviarMensaje(destinatario.get(), "[CONTROL][CONECTAR][SOLICITUD]" + mapper.writeValueAsString(solicitud));
        } catch (Exception e) {
          System.out.println("Error al enviar solicitud: " + e.getMessage());
        }
      } else {
        conectorServidor.enviarMensaje(remitente, "[CONTROL][CONECTAR][ERROR]El usuario no se encuentra escuchando");
      }
    }
  }

}
