package com.grupo.proyecto_AyD.negocio;

import java.io.IOException;
import java.net.Socket;

public class Conector extends GestionDeRed{

    public Conector() {
        super();
    }

    private void conectar(String ip, int puerto) {
        try {
            socket = new Socket(ip, puerto);
            System.out.println("Conectado a: " + ip + ":" + puerto);
        } catch (Exception e) {
            String mensaje = "Error conectando a: " + ip + ":" + puerto;
            System.out.println(mensaje);

            throw new RuntimeException(mensaje);
        }
    }

    public void ejecutarConexion(String ip, int puerto) {
        Thread thread = new Thread(() -> {
            try {
                conectar(ip, puerto);
                flow();
                this.enviarMensaje("INICIAR_SESION");
                try {
                    recibirMensajes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                cerrarConexion();
            }
        });

        thread.start();
    }
}
