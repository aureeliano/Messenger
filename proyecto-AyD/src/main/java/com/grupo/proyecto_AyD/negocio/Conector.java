package com.grupo.proyecto_AyD.negocio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Conector extends GestionDeRed{
    public void conectar(String ip, int puerto) {
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conectar(ip, puerto);
                    flow();
                    recibirMensajes();
                } finally {
                    cerrarConexion();
                }
            }
        });

        thread.start();
    }
}
