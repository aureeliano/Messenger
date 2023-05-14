package com.grupo.proyecto_AyD.modelo;

import com.grupo.proyecto_AyD.tipos.EstadoUsuario;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
  private static Servidor servidor = null;
  @Getter
  private String ip;
  @Getter
  @Setter
  private List<Usuario> usuariosConectados;
  @Getter
  @Setter
  private List<Sesion> chatsActivos;

  private Usuario usuario;

  private Servidor() {
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      System.out.println("Error al obtener la ip del servidor: " + e.getMessage());
    }

    usuario = Usuario.getUsuario();

    usuario.setNombre("SERVIDOR");
    usuario.setIp(this.ip);
    usuario.setEstado(EstadoUsuario.ESCUCHANDO);

    this.usuariosConectados = new ArrayList<>();
    this.chatsActivos = new ArrayList<>();
  }

  public static Servidor getServidor() {
    if (servidor == null) {
      servidor = new Servidor();
    }
    return servidor;
  }
}
