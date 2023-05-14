package com.grupo.proyecto_AyD.modelo;

import lombok.Getter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
  private static Servidor servidor = null;
  @Getter
  private String ip;
  private List<Usuario> usuariosConectados;
  private List<Sesion> chatsActivos;

  private Servidor() {
    try {
      this.ip = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      System.out.println("Error al obtener la ip del servidor: " + e.getMessage());
    }

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
