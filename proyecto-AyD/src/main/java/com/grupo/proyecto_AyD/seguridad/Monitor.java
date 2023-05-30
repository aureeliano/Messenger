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
import java.util.Objects;

public class Monitor {

  private static Monitor monitor = null;

  private ServerSocket serverSocket;
  private BufferedReader in;
  private boolean eschuchando = false;
  private ObjectMapper mapper;

  private Integer puertoMonitor;

  private Integer puertoServidor1;
  private LocalDateTime ultimoBeatServidor1;
  private Integer puertoServidor2;
  private LocalDateTime ultimoBeatServidor2;
  private Integer puertoActual;

  private Monitor() throws IOException {
    mapper = new ObjectMapper();

    puertoMonitor = 3000;
    ultimoBeatServidor1 = LocalDateTime.now();
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
            System.out.println("[MONITOR] Recibido mensaje: " + mensajeCrudo);
            if (mensajeCrudo.contains("[HEARTBEAT]")) {
              System.out.println("[MONITOR] Recibido heartbeat: " + mensajeCrudo);
              mensajeCrudo = mensajeCrudo.replace("[HEARTBEAT]", "");
              Integer puerto = Integer.parseInt(mensajeCrudo);

              // Si el puerto es el mismo que el actual, actualizo el ultimo beat
              if (puerto.equals(puertoServidor1)) {
                ultimoBeatServidor1 = LocalDateTime.now();
                // Si el servidor 1 no responde, cambio el puerto actual al servidor 2
                if (LocalDateTime.now().minusSeconds(5).isAfter(ultimoBeatServidor2) && !Objects.equals(puertoActual, puertoServidor1)) {
                  puertoActual = puertoServidor1;
                  puertoServidor2 = null;
                  System.out.println("[MONITOR] Cambiando puerto actual a: " + puertoActual);
                }
              } else if (puerto.equals(puertoServidor2)) {
                ultimoBeatServidor2 = LocalDateTime.now();
                if (LocalDateTime.now().minusSeconds(5).isAfter(ultimoBeatServidor1) && !Objects.equals(puertoActual, puertoServidor2)) {
                  puertoActual = puertoServidor2;
                  puertoServidor1 = null;
                  System.out.println("[MONITOR] Cambiando puerto actual a: " + puertoActual);
                }
              }
            } else if (mensajeCrudo.contains("[PUERTO]")) {
              if (puertoServidor1 == null) {
                puertoServidor1 = Integer.parseInt(mensajeCrudo.replace("[PUERTO]", ""));
                System.out.println("[MONITOR] Puerto servidor 1: " + puertoServidor1);
              } else if (puertoServidor2 == null) {
                puertoServidor2 = Integer.parseInt(mensajeCrudo.replace("[PUERTO]", ""));
                System.out.println("[MONITOR] Puerto servidor 2: " + puertoServidor2);
              }

              if (puertoActual == null) {
                puertoActual = puertoServidor1;
                System.out.println("[MONITOR] Puerto actual: " + puertoActual);
              }
            } else if (mensajeCrudo.contains("[SYNC]")) {
              Integer target = !puertoActual.equals(puertoServidor1) ? puertoServidor1 : puertoServidor2;
              if (target != null) {
                Socket socketServidor = new Socket("localhost", target);
                PrintWriter out = new PrintWriter(socketServidor.getOutputStream(), true);

                out.println(mensajeCrudo);

                out.flush();
                out.close();

                System.out.println("[MONITOR] Sincronizando servidor pasivo: " + target);
              }
            } else {
              String finalMensajeCrudo = mensajeCrudo;
              EjecutorBackoff.ejecutarConBackoffExponencial(() -> {
                Socket socketServidor = new Socket("localhost", puertoActual);
                PrintWriter out = new PrintWriter(socketServidor.getOutputStream(), true);
                out.println(finalMensajeCrudo);

                out.flush();
                out.close();
                System.out.println("[MONITOR] Mensaje reenviado al servidor: " + puertoActual);
              }, 16000, "direccionarMensaje");
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
