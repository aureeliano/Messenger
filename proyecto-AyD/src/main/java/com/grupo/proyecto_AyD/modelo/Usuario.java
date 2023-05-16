package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class Usuario {
    private static Usuario usuario;
    private String nombre;
    private int puerto;
    private String ip;
    private EstadoUsuario estado;
    private String id;


    private Usuario(String nombre, int puerto) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.ip = null;
        this.estado = EstadoUsuario.INACTIVO;
    }

    public static Usuario getUsuario() {
        if (usuario == null) {
            int puerto = ThreadLocalRandom.current().nextInt(3000, 4001);
            usuario = new Usuario(UUID.randomUUID().toString(), puerto);
        }
        return usuario;
    }

    private void updateIp() {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            System.out.println("Error al obtener la ip: " + e.getMessage());
        }
    }

    public void iniciarEscucha() {
        this.estado = EstadoUsuario.ESCUCHANDO;
        updateIp();
    }

    public void finalizarEscucha() {
        this.estado = EstadoUsuario.INACTIVO;
        updateIp();
    }

    public void iniciarConexion() {
        this.estado = EstadoUsuario.CONECTADO;
        updateIp();
    }

    public void actualizarInformacion(String nombre, int puerto) {
        this.nombre = nombre;
        //Por ahora no vamos a actualizar el puerto
        //this.puerto = puerto;
        updateIp();
    }

}
