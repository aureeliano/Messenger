package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class Conector implements ChatInterface  {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper;
    private Usuario usuario;


    public void init(String ip, int puerto) {
        this.usuario = Usuario.getUsuario();

        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            usuario.setIp(br.readLine());

            System.out.println("Intentando conectar a: " + ip + ":" + puerto);
            socket = new Socket(ip, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
            out.println(mapper.writeValueAsString(mensaje));
            System.out.println("Mensaje enviado");
        } catch (Exception e) {
            System.out.println("Error enviando mensaje: " + e.getMessage());
        }
    }
}
