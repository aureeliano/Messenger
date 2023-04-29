package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class Conector implements ChatInterface  {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper;
    private Usuario usuario;
    private String targetIp;
    private int targetPuerto;

    private static Conector conector;


    public void init(String ip, int puerto, boolean desdeChat) {
        this.usuario = Usuario.getUsuario();

        try {
            usuario.setIp(InetAddress.getLocalHost().getHostAddress());

            System.out.println("Intentando conectar a: " + ip + ":" + puerto);
            this.targetIp = ip;
            this.targetPuerto = puerto;

            mapper = new ObjectMapper();

            enviarMensaje(("[CONTROL][INICIAR_CHAT]"));
            enviarMensaje("[CONTROL]IP:" + usuario.getIp());
            enviarMensaje("[CONTROL]PUERTO:" + usuario.getPuerto());
        } catch (IOException e) {
            System.out.println("Error al iniciar el conector: " + e.getMessage());
        }
    }

    @Override
    public void enviarMensaje(String contenido) {
        Mensaje mensaje = new Mensaje(contenido);

        try {
            System.out.println("Intentando enviar mensaje: " );
            socket = new Socket(targetIp, targetPuerto);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(mapper.writeValueAsString(mensaje));

            if (!contenido.contains("[CONTROL]")) {
                Sesion.getSesion().getMensajes().add(mensaje);
            }

            out.flush();
            out.close();
            System.out.println("Mensaje enviado: " + contenido);
        } catch (Exception e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }
    }

    //Este metodo se usa en caso de que el usuario se desconecte
    public void finalizarConexion() {
        try {
            enviarMensaje("[CONTROL][FINALIZAR_CHAT]");
            manejarDesconexion();
        } catch (Exception e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
    }

    //Este metodo se usa en caso de que el compañero se desconecte
    public void manejarDesconexion() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
    }



    public static Conector getConector() {
        if (conector == null) {
            conector = new Conector();
        }

        return conector;
    }
}
