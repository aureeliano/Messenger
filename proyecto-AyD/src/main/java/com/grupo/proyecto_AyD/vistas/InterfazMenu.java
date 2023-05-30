package com.grupo.proyecto_AyD.vistas;

import java.util.List;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;

public interface InterfazMenu extends InterfazBase {
	void setConectados(List<UsuarioDTO> usuarios);
	void setEstado(String estado);
	void cambiarBotonEscucha(boolean escuchando);
	UsuarioDTO getUsuarioSeleccionado();

	void setNombre(String nombre);
	void setIp(String ip);
	void setPuerto(String puerto);
}
