package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Sesion {
    private String id;
    private Set<UsuarioDTO> usuarios;
    private List<Mensaje> mensajes;

    public Sesion() {
        this.id = UUID.randomUUID().toString();
        this.usuarios = new HashSet<>();
        this.mensajes = new ArrayList<>();
    }
}
