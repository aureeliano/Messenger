package com.grupo.proyecto_AyD.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class Configuracion implements Serializable {
    private String nombre;
    private Long puerto;
}
