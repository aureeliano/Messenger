package com.grupo.proyecto_AyD.negocio;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Listener extends GestionDeRed {

    public Listener() throws IOException {
        super();
    }

    /**
     * Metodo que permite entrar en modo de escucha
     * @return ip del usuario que esta escuchando
     */
    public void escuchar() {
        Usuario usuario = Usuario.getUsuario();
        try {
            serverSocket = new ServerSocket(usuario.getPuerto());
            System.out.println("Escuchando en el puerto " + usuario.getPuerto());

            this.sesionActiva = new Sesion();
            this.sesionActiva.getUsuarios().add(UsuarioDTO.fromUsuario(usuario));

            socket = serverSocket.accept();

            bufferEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferSalida = new PrintWriter(socket.getOutputStream(), true);
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
        Thread thread = new Thread(() -> {
            escuchar();
            while (escuchando) {
                try {
                    recibirMensajes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    cerrarConexion();
                }
            }
        });

        thread.start();
    }
}
