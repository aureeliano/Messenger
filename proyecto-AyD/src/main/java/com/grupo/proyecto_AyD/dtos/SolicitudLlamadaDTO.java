package com.grupo.proyecto_AyD.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SolicitudLlamadaDTO {
    private ExtremoDTO solicitante;
    private ExtremoDTO destino;
}
