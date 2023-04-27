package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorChat;
import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Listener implements ChatInterface {
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean eschuchando = false;

    private ObjectMapper mapper;
    private Usuario usuario;


    public void init(String ip, int puerto) {
        this.usuario = Usuario.getUsuario();

        try {
            usuario.setIp(InetAddress.getLocalHost().getHostAddress());

            serverSocket = new ServerSocket(puerto);

            this.eschuchando = true;
            mapper = new ObjectMapper();

            escuchar();

            System.out.println("Escuchando en puerto: " + usuario.getPuerto());
        } catch (IOException e) {
            System.out.println("Error al iniciar el listener: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void enviarMensaje(String mensaje) {

    }

    private void escuchar() {
        Thread thread = new Thread(() -> {
            try {
                while (eschuchando) {
                    System.out.println("Esperando conexion..." + serverSocket.toString());
                    Socket soc = serverSocket.accept();
                    out = new PrintWriter(soc.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(soc.getInputStream()));

                    Mensaje mensaje = mapper.readValue(in.readLine(), Mensaje.class);

                    System.out.println("MENSAJE RECIBIDO: " + mensaje.getMensaje());

                    if (mensaje.getMensaje().contains("[CONTROL]")) {
                        if (mensaje.getMensaje().contains("[INICIAR_CHAT]")) {
                            ControladorChat.getControlador();
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
        this.eschuchando = false;
    }
}
