package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Mensaje {
    private Instant fecha;
    private String mensaje;
    private String id;
}
