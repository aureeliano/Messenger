package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Usuario {
    private String nombre;
    private Long puerto;
    private String ip;
    private EstadoUsuario estado;
    private List<String> ipBloqueadas;

    public Usuario(String nombre, Long puerto) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.ip = null;
        this.estado = EstadoUsuario.INACTIVO;
        this.ipBloqueadas = new ArrayList<>();
    }
}
