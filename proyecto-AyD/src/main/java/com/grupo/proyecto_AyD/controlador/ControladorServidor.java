package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.ConectorServidor;
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
  private ConectorServidor conectorServidor;

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
  }


  public void actualizarConectados(List<Usuario> conectados) {
    vistaServidor.setUsuariosConectados(conectados.stream().map(UsuarioDTO::fromUsuario).toList());
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
