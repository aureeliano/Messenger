package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Clase que se encarga de escuchar las peticiones de los clientes
 * Tiene similitudes con la clase Listener que sigue la arquitectura P2P
 * @see com.grupo.proyecto_AyD.red.Listener
 * Esta sigue la arquitectura cliente-servidor
 */
public class ListenerServidor {
  private ServerSocket serverSocket;
  private BufferedReader in;
  private boolean eschuchando = false;
  private ObjectMapper mapper;
  private Servidor servidor;

  private ConectorServidor conectorServidor;

  private static ListenerServidor listenerServidor;

  private ListenerServidor() {
    this.servidor = Servidor.getServidor();
    this.mapper = new ObjectMapper();
  }

  public static ListenerServidor getListenerServidor() {
    if (listenerServidor == null) {
      listenerServidor = new ListenerServidor();
    }
    return listenerServidor;
  }

  public void init() {
    try {
      serverSocket = new ServerSocket(3000);
      this.eschuchando = true;
      escuchar();

      System.out.println("[SERVIDOR] Escuchando en puerto: 3000");
    } catch (Exception e) {
      System.out.println("Error al iniciar el listener: " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private void escuchar() {
    Thread thread = new Thread(() -> {
      try {
        while (eschuchando) {
          System.out.println("[SERVIDOR] Esperando conexiones...");

          Socket socket = serverSocket.accept();
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String mensajeCrudo = in.readLine();
          System.out.println("[SERVIDOR] Mensaje recibido: " + mensajeCrudo);

          if (mensajeCrudo != null) {
            Mensaje mensaje = mapper.readValue(mensajeCrudo, Mensaje.class);

            if (mensaje.getMensaje().contains("[CONTROL]")) {
              if (mensaje.getMensaje().contains("[CONEXION]")){
                if (!this.servidor.getUsuariosConectados().contains(mensaje.getRemitente())) {
                  this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
                  ControladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());

                  System.out.println("[SERVIDOR] Usuario conectado: " + mensaje.getRemitente());
                  this.conectorServidor.enviarMensaje(mensaje.getRemitente(), "[CONTROL][CONEXION][OK]");
                }
              }
            }
          }

        }
      } catch (Exception e) {
        System.out.println("Error al escuchar: " + e.getMessage());
      }
    });

    thread.start();
  }

}
