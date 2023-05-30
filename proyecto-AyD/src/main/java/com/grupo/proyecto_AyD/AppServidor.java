package com.grupo.proyecto_AyD;

import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.seguridad.Monitor;

import java.io.IOException;

public class AppServidor {

  public static void main( String[] args ) throws IOException {
    new ControladorServidor();
  }
}
