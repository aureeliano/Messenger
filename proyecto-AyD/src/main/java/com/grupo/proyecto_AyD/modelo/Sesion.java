package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Sesion {
    private String id;
    private Set<Usuario> usuarios;
    private List<Mensaje> mensajes;

    public static Sesion sesion;

    public Sesion() {
        this.id = UUID.randomUUID().toString();
        this.usuarios = new HashSet<>();
        this.mensajes = new ArrayList<>();
    }

    public static Sesion getSesion() {
        if (sesion == null) {
            sesion = new Sesion();
        }

        return sesion;
    }
}
