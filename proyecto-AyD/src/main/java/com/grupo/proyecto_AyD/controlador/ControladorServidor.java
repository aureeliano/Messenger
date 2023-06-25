package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.ConectorServidor;
import com.grupo.proyecto_AyD.red.InterfazConectorServidor;
import com.grupo.proyecto_AyD.red.ListenerServidor;
import com.grupo.proyecto_AyD.vistas.InterfazServidor;
import com.grupo.proyecto_AyD.vistas.VistaServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorServidor implements ActionListener {
  private Servidor servidor;
  private InterfazServidor vistaServidor;
  private ListenerServidor listenerServidor;
  private InterfazConectorServidor conectorServidor;

  public ControladorServidor() {
    vistaServidor = new VistaServidor();
    servidor = Servidor.getServidor();
    vistaServidor.setActionListener(this);

    listenerServidor = new ListenerServidor(this);
    conectorServidor = new ConectorServidor();

    listenerServidor.init(conectorServidor);

    conectorServidor.init();


    vistaServidor.mostrar();
    vistaServidor.setIpServidor(servidor.getIp());
    String puerto = servidor.getPuerto();
    vistaServidor.setPuerto(servidor.getPuerto());

    if (puerto.equals("3002")) {
      vistaServidor.habilitarCambioAPrincipal(true);
    }
  }


  public void actualizarConectados(List<Usuario> conectados) {
    vistaServidor.setUsuariosConectados(conectados.stream().map(UsuarioDTO::fromUsuario).toList());
  }

  public void manejarCambioDePuerto() {
    listenerServidor.moverAPrincipal();
    if (servidor.getPuerto().equals("3001")) {
      vistaServidor.mostrarMensaje("Servidor cambiado a puerto principal");
      vistaServidor.habilitarCambioAPrincipal(false);
    } else {
      vistaServidor.mostrarMensaje("No se pudo cambiar el servidor a puerto principal, ya que se encuentra en uso");
    }
    vistaServidor.setPuerto(servidor.getPuerto());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String comando = e.getActionCommand();

    switch (comando) {
      case "salir" -> System.exit(0);
      case "cambiarPuerto" -> manejarCambioDePuerto();
    }
  }
}
