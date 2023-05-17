package com.grupo.proyecto_AyD.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExtremoDTO {
    private String ip;
    private int puerto;
    private String nombre;
}
