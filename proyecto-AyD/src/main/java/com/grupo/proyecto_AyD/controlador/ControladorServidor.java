package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.ConectorServidor;
import com.grupo.proyecto_AyD.red.ListenerServidor;
import com.grupo.proyecto_AyD.vistas.InterfazServidor;
import com.grupo.proyecto_AyD.vistas.VistaServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorServidor implements ActionListener {
  private static ControladorServidor controladorServidor = null;
  private Servidor servidor;
  private InterfazServidor vistaServidor;
  private ListenerServidor listenerServidor;

  private ControladorServidor() {
    vistaServidor = new VistaServidor();
    servidor = Servidor.getServidor();
    vistaServidor.setActionListener(this);

    listenerServidor = ListenerServidor.getListenerServidor();
    listenerServidor.init();
  }

  public static ControladorServidor getControlador() {
    if (controladorServidor == null) {
      controladorServidor = new ControladorServidor();
    }

    controladorServidor.vistaServidor.mostrar();
    controladorServidor.vistaServidor.setIpServidor(controladorServidor.servidor.getIp());

    return controladorServidor;
  }

  public static void actualizarConectados(List<Usuario> conectados) {
    controladorServidor.vistaServidor.setUsuariosConectados(conectados.stream().map(UsuarioDTO::fromUsuario).toList());
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
