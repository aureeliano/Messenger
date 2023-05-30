package com.grupo.proyecto_AyD.seguridad;

public class EjecutorBackoff {
  public static void ejecutarConBackoffExponencial(RunnableConExcepcion runnable, int tiempoMaximo, String nombre) {
    Thread thread = new Thread(() -> {
      //Espera como minimo 500ms
      int tiempoEspera = 500;
      boolean exito = false;

      while (!exito && tiempoEspera < tiempoMaximo) {
        try {
          runnable.run();
          exito = true;
        } catch (Exception e) {
          System.out.println("[" + nombre + "] Error: " + e.getMessage() + " Reintentando en " + tiempoEspera + "ms");
          try {
            Thread.sleep(tiempoEspera);
          } catch (InterruptedException interruptedException) {
          }
          tiempoEspera *= 2;
        }
      }

      if (!exito) {
        System.out.println("[" + nombre + "]Error: No se pudo ejecutar la operacion");
      }
    });

    thread.start();
  }
}
