package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorChat;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    private static Listener listener;


    public void init(String ip, int puerto, boolean desdeChat) {
        this.usuario = Usuario.getUsuario();

        try {
            usuario.setIp(InetAddress.getLocalHost().getHostAddress());
            usuario.iniciarEscucha();

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
                    System.out.println("MENSAJE RECIBIDO: " + mensajeCrudo);

                    if (mensajeCrudo != null) {
                        Mensaje mensaje = mapper.readValue(mensajeCrudo, Mensaje.class);

                        if (mensaje.getMensaje().contains("[CONTROL]")) {
                            if (mensaje.getMensaje().contains("PUERTO:")) {
                                this.puerto = mensaje.getMensaje().replace("[CONTROL]PUERTO:", "");
                            }

                            if (mensaje.getMensaje().contains("IP:")) {
                                this.ip = mensaje.getMensaje().replace("[CONTROL]IP:", "");
                            }

                            if (puerto != null && ip != null && !desdeChat) {
                                controlador = ControladorChat.getControlador(ip, puerto, true);
                            } else {
                                controlador = ControladorChat.getControlador();
                            }

                            if (mensaje.getMensaje().contains("[FINALIZAR_CHAT]")) {
                                assert controlador != null;

                                pararEscucha();
                                controlador.finalizarChat();
                            }
                        } else {
                            // Controlador nunca deberia ser null, primero llegan los mensajes de control
                            Sesion sesion = Sesion.getSesion();
                            sesion.getMensajes().add(mensaje);
                            assert controlador != null;

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
