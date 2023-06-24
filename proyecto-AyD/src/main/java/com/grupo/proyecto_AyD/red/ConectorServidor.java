package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

public class ConectorServidor {

  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;
  private ObjectMapper mapper;
  private Usuario usuario;
  private boolean beat;

  @Setter
  private ListenerServidor listenerServidor;


  public void init() {
    this.usuario = Usuario.getUsuario();

    try {
      usuario.setIp(InetAddress.getLocalHost().getHostAddress());
      this.beat = true;
      iniciarBeats();
      mapper = new ObjectMapper();

    } catch (IOException e) {
      System.out.println("Error al iniciar el conector: " + e.getMessage());
    }
  }

  public void enviarMensaje(Usuario usuario, String contenido) {
    Mensaje mensaje = new Mensaje(contenido);

    try {
      System.out.println("Intentando enviar mensaje: " + contenido);
      socket = new Socket(usuario.getIp(), usuario.getPuerto());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println(mapper.writeValueAsString(mensaje));

      out.flush();
      out.close();
      System.out.println("Mensaje enviado: " + contenido);

    } catch (Exception e) {
      System.out.println("Error enviando mensaje: " + e.getMessage());
    }

  }

  public void reenviarMensaje(Usuario usuario, Mensaje mensaje) {
    try {
      System.out.println("Intentando reenviar mensaje: " + mensaje.getMensaje());
      socket = new Socket(usuario.getIp(), usuario.getPuerto());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println(mapper.writeValueAsString(mensaje));

      out.flush();
      out.close();
      System.out.println("Mensaje reenviado: " + mensaje.getMensaje());

    } catch (Exception e) {
      System.out.println("Error enviando mensaje: " + e.getMessage());
    }
  }

  //Este metodo se usa en caso de que el usuario se desconecte
  public void finalizarConexion(Usuario usuario) {
    try {
      enviarMensaje(usuario, "[CONTROL][FINALIZAR_CHAT]");
      manejarDesconexion();
    } catch (Exception e) {
      System.out.println("Error al cerrar el socket: " + e.getMessage());
    }
  }

  //Este metodo se usa en caso de que el compañero se desconecte
  public void manejarDesconexion() {
    try {
      socket.close();
    } catch (IOException e) {
    }
  }


  private void iniciarBeats() {
    enviarPuerto();

    new Thread(() -> {
      while (beat) {
        try {
          Thread.sleep(2000);
          enviarHeartbeat();
        } catch (InterruptedException e) {
          System.out.println("Error en el heartbeat: " + e.getMessage());
        }
      }
    }).start();
  }

  private void enviarHeartbeat() {
    try {
      socket = new Socket("localhost", 3000);
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println("[HEARTBEAT]" + listenerServidor.getPuertoEscucha());

      out.flush();
      out.close();
    } catch (Exception e) {
    }
  }

  private void enviarPuerto() {
    try {
      socket = new Socket("localhost", 3000);
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println("[PUERTO]" + listenerServidor.getPuertoEscucha());

      out.flush();
      out.close();
    } catch (Exception e) {
    }
  }

  public void sincronizar() {
    try {
      socket = new Socket("localhost", 3002);
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println("[SYNC][USUARIOS]" + mapper.writeValueAsString(Servidor.getServidor().getUsuariosConectados()));
      out.flush();
      out.close();

      socket = new Socket("localhost", 3002);
      out = new PrintWriter(socket.getOutputStream(), true);
      out.println("[SYNC][CHATS]" + mapper.writeValueAsString(Servidor.getServidor().getChatsActivos()));
      out.flush();

      out.close();
    } catch (Exception e) {
    }
  }

  public void terminar() {
    this.beat = false;
    manejarDesconexion();
  }
}
