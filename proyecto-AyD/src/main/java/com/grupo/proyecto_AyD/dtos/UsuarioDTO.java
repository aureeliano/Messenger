package com.grupo.proyecto_AyD.dtos;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private String nombre;
    private String id;
    private String ip;
    private int puerto;
    private EstadoUsuario estado;

    public static UsuarioDTO fromUsuario(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEstado(usuario.getEstado());
        usuarioDTO.setIp(usuario.getIp());
        usuarioDTO.setPuerto(usuario.getPuerto());

        return usuarioDTO;
    }

    @Override
    public String toString() {
        return "Usuario: " + this.nombre + " Estado: " + this.estado + " IP: " + this.ip + " Puerto: " + this.puerto;
    }
}
