package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
            usuario = new Usuario(UUID.randomUUID().toString(), 8080);
        }
        return usuario;
    }

    public void iniciarEscucha() {
        this.estado = EstadoUsuario.ESCUCHANDO;
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            System.out.println("Error al obtener la ip: " + e.getMessage());
        }
    }
}
