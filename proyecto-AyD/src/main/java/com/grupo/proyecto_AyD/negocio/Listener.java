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

public class Listener extends GestionDeRed {

    /**
     * Metodo que permite entrar en modo de escucha
     * @return ip del usuario que esta escuchando
     */
    public String escuchar() {
        Usuario usuario = Usuario.getUsuario();
        usuario.setEstado(EstadoUsuario.ESCUCHANDO);
        try {
            serverSocket = new ServerSocket(usuario.getPuerto());
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
