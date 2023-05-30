package com.grupo.proyecto_AyD;

import com.grupo.proyecto_AyD.seguridad.Monitor;

import java.io.IOException;

public class RunMonitor {
  public static void main(String[] args) throws IOException {
    Monitor.getMonitor().init();
  }
}
