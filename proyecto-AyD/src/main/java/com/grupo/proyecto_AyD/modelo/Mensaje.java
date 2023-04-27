package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Mensaje {
    private Date fecha;
    private String mensaje;
    private String id;

    public Mensaje(String mensaje) {
        this.fecha = Date.from(Instant.now());
        this.mensaje = mensaje;
        this.id = UUID.randomUUID().toString();
    }
}
