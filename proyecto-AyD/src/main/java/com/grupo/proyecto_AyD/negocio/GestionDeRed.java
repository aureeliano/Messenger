package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorChat;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GestionDeRed {
    Socket socket;
    ServerSocket serverSocket;
    DataInputStream bufferEntrada = null;
    DataOutputStream bufferSalida = null;
    boolean escuchando = false;
    Sesion sesionActiva;
    final ObjectMapper objectMapper = new ObjectMapper();

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

    /**
     * Metodo que permite recibir mensajes, se parsean a objetos y se agregan a la lista de mensajes
     */
    public void recibirMensajes() throws IOException {
        Mensaje mensaje;

        try {
            mensaje = objectMapper.readValue(bufferEntrada.readUTF(), Mensaje.class);

            if (mensaje.getMensaje().equals("INICIAR_SESION")) {
                ControladorChat.getControlador();
            }

            this.sesionActiva.getMensajes().add(mensaje);

            System.out.println("Mensaje recibido: " + mensaje.getMensaje());
        } catch (Exception e) {
            System.out.println("Error al recibir mensaje");
            throw e;
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
            System.out.println("Mensaje enviado: " + mensaje.getMensaje());
        } catch (Exception e) {
            System.out.println("Error al enviar mensaje");
            throw new RuntimeException(e);
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

}
