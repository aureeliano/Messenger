package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {
    private String nombre;
    private Long puerto;
    private String ip;

    public Usuario(String nombre, Long puerto, String ip) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.ip = ip;
    }
}
