package com.grupo.proyecto_AyD.seguridad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.modelo.Mensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Monitor {

  private static Monitor monitor = null;

  private ServerSocket serverSocket;
  private BufferedReader in;
  private boolean eschuchando = false;
  private ObjectMapper mapper;

  private int puertoMonitor;

  private int puertoServidor1;
  private LocalDateTime ultimoBeatServidor1;
  private int puertoServidor2;
  private LocalDateTime ultimoBeatServidor2;
  private int puertoActual;

  private Monitor() throws IOException {
    mapper = new ObjectMapper();

    puertoMonitor = 3000;
    puertoServidor1 = 3001;
    ultimoBeatServidor1 = LocalDateTime.now();
    puertoServidor2 = 3002;
    ultimoBeatServidor2 = LocalDateTime.now();

    serverSocket = new ServerSocket(puertoMonitor);
  }

  public static Monitor getMonitor() throws IOException {
    if (monitor == null) {
      monitor = new Monitor();
    }
    return monitor;
  }

  public void init() {
    new ControladorServidor(puertoServidor1);
    new ControladorServidor(puertoServidor2);
    this.puertoActual = puertoServidor1;
    eschuchando = true;

    escuchar();
  }

  public void escuchar(){
    Thread thread = new Thread(() -> {
      try {
        while (eschuchando) {
          Socket socket = serverSocket.accept();
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

          String mensajeCrudo = in.readLine();

          if (mensajeCrudo != null) {
            if (mensajeCrudo.contains("[HEARTBEAT]")) {
              System.out.println("[MONITOR] Recibido heartbeat: " + mensajeCrudo);
              mensajeCrudo = mensajeCrudo.replace("[HEARTBEAT]", "");
              int puerto = Integer.parseInt(mensajeCrudo);

              // Si el puerto es el mismo que el actual, actualizo el ultimo beat
              if (puerto == puertoServidor1) {
                ultimoBeatServidor1 = LocalDateTime.now();
                // Si el servidor 1 no responde, cambio el puerto actual al servidor 2
                if (LocalDateTime.now().minusSeconds(5).isAfter(ultimoBeatServidor1) && puertoActual != puertoServidor1) {
                  puertoActual = puertoServidor1;
                  System.out.println("[MONITOR] Cambiando puerto actual a: " + puertoActual);
                }
              } else if (puerto == puertoServidor2) {
                ultimoBeatServidor2 = LocalDateTime.now();
                if (LocalDateTime.now().minusSeconds(5).isAfter(ultimoBeatServidor1) && puertoActual != puertoServidor2) {
                  puertoActual = puertoServidor2;
                  System.out.println("[MONITOR] Cambiando puerto actual a: " + puertoActual);
                }
              }
            } else {
              Socket socketServidor = new Socket("localhost", puertoActual);
              PrintWriter out = new PrintWriter(socketServidor.getOutputStream(), true);
              out.println(mensajeCrudo);

              out.flush();
              out.close();
              System.out.println("[MONITOR] Mensaje reenviado al servidor: ");
            }
          }
        }
      } catch (Exception e) {
        System.out.println("Error al escuchar: " + e.getMessage());
      }
    });

    thread.start();
  };


}
