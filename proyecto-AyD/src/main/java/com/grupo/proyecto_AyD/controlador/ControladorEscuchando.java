package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazEscuchando;
import com.grupo.proyecto_AyD.vistas.VistaEscuchando;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEscuchando implements ActionListener {
    private static ControladorEscuchando controladorEscuchando = null;
    private InterfazEscuchando vistaEscuchando;
    private Usuario usuario;
    private Conector conector;

    private ControladorEscuchando() {
        vistaEscuchando = new VistaEscuchando();
        usuario = Usuario.getUsuario();
        conector = Conector.getConector();
        vistaEscuchando.setActionListener(this);
    }

    public static ControladorEscuchando getControlador() {
        if (controladorEscuchando == null) {
            controladorEscuchando = new ControladorEscuchando();
        }

        controladorEscuchando.conector.enviarMensaje("[CONTROL][ESCUCHANDO][INICIAR]");

        controladorEscuchando.vistaEscuchando.setIpEscuchando(Usuario.getUsuario().getIp());
        controladorEscuchando.vistaEscuchando.setPuerto(Usuario.getUsuario().getPuerto());
        controladorEscuchando.vistaEscuchando.mostrar();
        return controladorEscuchando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "volver":
                conector.enviarMensaje("[CONTROL][ESCUCHANDO][TERMINAR]");
                ControladorMainMenu.getControlador();
                vistaEscuchando.esconder();
                break;
        }
    }
}
