package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

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
            this.targetIp = ip;
            this.targetPuerto = puerto;

            mapper = new ObjectMapper();
        } catch (IOException e) {
            System.out.println("Error al iniciar el conector: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> enviarMensaje(String contenido) {
        Mensaje mensaje = new Mensaje(contenido);

        try {
            System.out.println("Intentando enviar mensaje: " + contenido);
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

            return Sesion.getSesion().getMensajes()
                    .stream()
                    .sorted(Comparator.comparing(Mensaje::getFecha))
                    .toList();
        } catch (Exception e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }

        return Sesion.getSesion().getMensajes()
                .stream()
                .sorted(Comparator.comparing(Mensaje::getFecha))
                .toList();
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

    public void enviarMensajeAServidor(String mensaje, String ip) {
        this.targetIp = ip;
        this.targetPuerto = 3000;

        enviarMensaje(mensaje);
    }
}
