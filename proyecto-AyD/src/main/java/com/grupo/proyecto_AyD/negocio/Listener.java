package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.UUID;

public class Listener {
    private Socket socket;
    private ServerSocket serverSocket;
    private DataInputStream bufferEntrada = null;
    private DataOutputStream bufferSalida = null;
    private boolean escuchando = false;
    private Sesion sesionActiva;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Metodo que permite entrar en modo de escucha
     * @return ip del usuario que esta escuchando
     */
    public String escuchar() {
        Usuario usuario = Usuario.getUsuario();
        usuario.setEstado(EstadoUsuario.ESCUCHANDO);
        try {
            ServerSocket serverSocket = new ServerSocket(usuario.getPuerto());
            System.out.println("Escuchando en el puerto " + usuario.getPuerto());

            socket = serverSocket.accept();
            this.escuchando = true;
            System.out.println("Escuchando en ip " + socket.getInetAddress().getHostAddress());

            this.sesionActiva = new Sesion();
            this.sesionActiva.getUsuarios().add(UsuarioDTO.fromUsuario(usuario));

            return socket.getInetAddress().getHostAddress();
        } catch (Exception e) {
            String mensaje = "Error al levantando listener";
            System.out.println(mensaje);
            usuario.setEstado(EstadoUsuario.INACTIVO);

            throw new RuntimeException(mensaje);
        }
    }

    public void flow() {
        try {
            bufferEntrada = new DataInputStream(socket.getInputStream());
            bufferSalida = new DataOutputStream(socket.getOutputStream());
            bufferSalida.flush();
        } catch (Exception e) {
            String mensaje = "Error al crear los buffers";
            System.out.println(mensaje);
            throw new RuntimeException(mensaje);
        }
    }

    public void cerrarConexion() {
        try {
            bufferEntrada.close();
            bufferSalida.close();
            socket.close();

            this.escuchando = false;
        } catch (Exception e) {
            String mensaje = "Error al cerrar la conexion";
            System.out.println(mensaje);

            throw new RuntimeException(mensaje);
        }
    }

    /**
     * Metodo que permite recibir mensajes, se parsean a objetos y se agregan a la lista de mensajes
     */
    public void recibirMensajes() {
        Mensaje mensaje;

        try {
            mensaje = objectMapper.readValue(bufferEntrada.readUTF(), Mensaje.class);
            this.sesionActiva.getMensajes().add(mensaje);

            System.out.println("Mensaje recibido: " + mensaje.getMensaje());
        } catch (Exception e) {
            System.out.println("Error al recibir mensaje");
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo que permite enviar mensajes
     * @param contenido el string a enviar
     */
    public void enviarMensaje(String contenido) {
        try {
            Mensaje mensaje = new Mensaje(contenido);

            bufferSalida.writeUTF(objectMapper.writeValueAsString(mensaje));
            bufferSalida.flush();
        } catch (Exception e) {
            System.out.println("Error al enviar mensaje");
            throw new RuntimeException(e);
        }
    }

    /**
     * Este metodo permite ejecutar la escucha en un hilo aparte
     * para que no se bloquee el hilo principal
     * Llamar desde el controlador
     */
    public void ejecutarEscucha() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (escuchando) {
                    try {
                        escuchar();
                        flow();
                        recibirMensajes();
                    } finally {
                        cerrarConexion();
                    }
                }
            }
        });

        thread.start();
    }
}
