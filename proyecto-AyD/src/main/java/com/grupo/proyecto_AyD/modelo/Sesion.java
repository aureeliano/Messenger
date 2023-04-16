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

    public Sesion(String id, Set<Usuario> usuarios, List<Mensaje> mensajes) {
        this.id = id;
        this.usuarios = usuarios;
        this.mensajes = mensajes;
    }

    public Sesion() {
        this.id = UUID.randomUUID().toString();
        this.usuarios = new HashSet<>();
        this.mensajes = new ArrayList<>();
    }
}
