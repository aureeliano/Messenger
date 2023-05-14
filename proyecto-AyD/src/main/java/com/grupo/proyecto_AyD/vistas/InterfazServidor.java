package com.grupo.proyecto_AyD.vistas;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;

import java.util.List;

public interface InterfazServidor extends InterfazBase {
  void setUsuariosConectados(List<UsuarioDTO> usuariosConectados);
  void setIpServidor(String ipServidor);
}
