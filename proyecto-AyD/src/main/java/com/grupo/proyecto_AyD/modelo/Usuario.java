package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.negocio.GestionDeRed;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
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

    private GestionDeRed interfazActiva;

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

    private void updateIp() {
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            this.ip = br.readLine();
        } catch (Exception e) {
            System.out.println("Error al obtener la ip: " + e.getMessage());
        }
    }

    public void iniciarEscucha(GestionDeRed interfazActiva) {
        this.estado = EstadoUsuario.ESCUCHANDO;
        this.interfazActiva = interfazActiva;
        updateIp();
    }

    public void finalizarEscucha() {
        this.estado = EstadoUsuario.INACTIVO;
        updateIp();
    }

    public void iniciarConexion(GestionDeRed interfazActiva) {
        this.estado = EstadoUsuario.CONECTADO;
        this.interfazActiva = interfazActiva;
        updateIp();
    }

    public void actualizarInformacion(String nombre, int puerto) {
        this.nombre = nombre;
        this.puerto = puerto;
        updateIp();
    }

    public GestionDeRed getInterfazActiva() {
        return interfazActiva;
    }
}
