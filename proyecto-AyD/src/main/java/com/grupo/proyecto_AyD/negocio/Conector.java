package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.modelo.Mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Conector implements ChatInterface  {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper;


    public void init(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            mapper = new ObjectMapper();
        } catch (IOException e) {
            System.out.println("Error al iniciar el conector: " + e.getMessage());
            throw new RuntimeException(e);
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
