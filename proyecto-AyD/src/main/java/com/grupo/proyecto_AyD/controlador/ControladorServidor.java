package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.vistas.InterfazServidor;
import com.grupo.proyecto_AyD.vistas.VistaServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorServidor implements ActionListener {
  private static ControladorServidor controladorServidor = null;
  private Servidor servidor;
  private InterfazServidor vistaServidor;

  private ControladorServidor() {
    vistaServidor = new VistaServidor();
    servidor = Servidor.getServidor();
  }

  public static ControladorServidor getControlador() {
    if (controladorServidor == null) {
      controladorServidor = new ControladorServidor();
    }

    controladorServidor.vistaServidor.mostrar();

    return controladorServidor;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String comando = e.getActionCommand();

    switch (comando) {
      case "salir":
        System.exit(0);
        break;
    }
  }
}
