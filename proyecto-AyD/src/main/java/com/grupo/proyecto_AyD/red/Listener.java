package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.*;
import com.grupo.proyecto_AyD.dtos.SolicitudLlamadaDTO;
import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.encriptacion.Encriptacion;
import com.grupo.proyecto_AyD.encriptacion.Encriptador;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.GestorDeChats;
import com.grupo.proyecto_AyD.negocio.InterfazGestorChats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Clase que se encarga de escuchar las conexiones entrantes, con arquitectura P2P
 * @see com.grupo.proyecto_AyD.red.ListenerServidor
 * que sigue la arquitectura cliente-servidor
 */
public class Listener implements ChatInterface {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader in;
    private boolean eschuchando = false;

    private ObjectMapper mapper;
    private Usuario usuario;
    private String puerto;
    private String ip;
    private final List<SolicitudLlamadaDTO> solicitudes = new ArrayList<>();

    private static Listener listener;

    private String claveDesencriptacion;
    private InterfazGestorChats gestorChats;

    private final Encriptador encriptador = new Encriptacion();


    public void init(String ip, int puerto, boolean desdeChat) {
        this.usuario = Usuario.getUsuario();
        this.gestorChats = GestorDeChats.getGestor();

        try {
            usuario.setIp(InetAddress.getLocalHost().getHostAddress());
            serverSocket = new ServerSocket(puerto);

            this.eschuchando = true;
            mapper = new ObjectMapper();

            escuchar(desdeChat);

            System.out.println("Escuchando en puerto: " + usuario.getPuerto());
        } catch (IOException e) {
            System.out.println("Error al iniciar el listener: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Mensaje> enviarMensaje(String mensaje) {
        return new ArrayList<>();
    }


    private void escuchar(boolean desdeChat) {
        Thread thread = new Thread(() -> {
            try {
                ControladorChat controlador = null;

                while (eschuchando) {
                    System.out.println("Esperando conexion..." + serverSocket.toString());
                    Socket soc = serverSocket.accept();
                    in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

                    String mensajeCrudo = in.readLine();

                    if (mensajeCrudo != null) {
                        Mensaje mensaje = mapper.readValue(mensajeCrudo, Mensaje.class);
                        String contenido = mensaje.getMensaje();

                        if (contenido != null && contenido.contains("[CONTROL]")) { //Significa que es un mensaje de control no encriptado

                            System.out.println("MENSAJE DE CONTROL RECIBIDO: " + contenido);

                            contenido = contenido.replace("[CONTROL]", "");
                            if (contenido.contains("[CONEXION_CLIENTE][OK]")) {
                                gestorChats.confirmarConexion();
                            }

                            if (contenido.contains("PUERTO:")) {
                                this.puerto = mensaje.getMensaje().replace("[CONTROL]PUERTO:", "");
                            }

                            if (contenido.contains("IP:")) {
                                this.ip = mensaje.getMensaje().replace("[CONTROL]IP:", "");
                            }

                            if (contenido.contains("[FINALIZAR_CHAT]")) {
                                pararEscucha();
                                gestorChats.cerrarChat();
                            }

                            if (contenido.contains("[CONECTAR][SOLICITUD]")) {
                                contenido = contenido.replace("[CONECTAR][SOLICITUD]", "");
                                SolicitudLlamadaDTO solicitud = mapper.readValue(contenido, SolicitudLlamadaDTO.class);
                                solicitudes.add(solicitud);

                                ControladorLlamada.getControladorLlamada().setDatosSolicitud(solicitud);
                            }

                            if (contenido.contains("[CONECTAR][ACEPTAR]")) {
                                contenido = contenido.replace("[CONECTAR][ACEPTAR]", "");
                                gestorChats.enviarClaveDeEncriptacion(mensaje);
                            }

                            if (contenido.contains("[CONECTADOS]")) {
                                contenido = contenido.replace("[CONECTADOS]", "");
                                gestorChats.actualizarListaConectados(contenido);
                            }

                            if (contenido.contains("[CLAVE]")) {
                                contenido = contenido.replace("[CLAVE]", "");
                                this.claveDesencriptacion = contenido;

                                System.out.println("Clave desencriptacion recibida: " + this.claveDesencriptacion);
                            }

                            if (contenido.contains("[CONECTAR][ERROR]")) {
                                contenido = contenido.replace("[CONECTAR][ERROR]", "");
                                ControladorMainMenu.getControlador().setEstado(contenido);
                            }

                        } else {
                            Mensaje mensajeEncriptado = mapper.readValue(mensajeCrudo, Mensaje.class);
                            System.out.println("Mensaje encriptado recibido: " + mensajeEncriptado.getContenidoEncriptado());

                            Mensaje mensajeDesencriptado = mapper.readValue(encriptador.desencriptar(mensajeEncriptado.getContenidoEncriptado(), claveDesencriptacion), Mensaje.class);
                            System.out.println("Mensaje desencriptado: " + mensajeDesencriptado);

                            Sesion sesion = Sesion.getSesion();
                            sesion.getMensajes().add(mensajeDesencriptado);
                            if (controlador == null) {
                                controlador = ControladorChat.getControlador();
                            }

                            controlador
                                    .getVistaChat()
                                    .setMensajes(
                                            sesion
                                                .getMensajes()
                                                .stream()
                                                .sorted(Comparator.comparing(Mensaje::getFecha))
                                                .toList()
                                    );
                        }
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        thread.start();
    }

    public void pararEscucha() {
        usuario.finalizarEscucha();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
        this.eschuchando = false;
    }

    public static Listener getListener(){
        if (listener == null) {
            listener = new Listener();
        }

        return  listener;
    }
}
