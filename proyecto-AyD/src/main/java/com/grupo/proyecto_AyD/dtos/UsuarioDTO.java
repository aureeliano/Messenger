package com.grupo.proyecto_AyD.dtos;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String nombre;
    private String id;
    private EstadoUsuario estado;

    public static UsuarioDTO fromUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEstado(usuario.getEstado());

        return usuarioDTO;
    }
}
