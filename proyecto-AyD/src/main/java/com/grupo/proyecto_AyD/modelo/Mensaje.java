package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class Mensaje {
    private Instant fecha;
    private String mensaje;
    private String id;

    public Mensaje(String mensaje) {
        this.fecha = Instant.now();
        this.mensaje = mensaje;
        this.id = UUID.randomUUID().toString();
    }
}
