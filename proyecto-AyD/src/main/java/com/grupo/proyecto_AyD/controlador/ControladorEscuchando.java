package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazEscuchando;
import com.grupo.proyecto_AyD.vistas.VistaEscuchando;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControladorEscuchando implements ActionListener {
    private static ControladorEscuchando controladorEscuchando = null;
    private InterfazEscuchando vistaEscuchando;
    private Usuario usuario;
    private Listener listener;

    private ControladorEscuchando() {
        vistaEscuchando = new VistaEscuchando();
        usuario = Usuario.getUsuario();
        vistaEscuchando.setActionListener(this);
    }

    public static ControladorEscuchando getControlador() {
        if (controladorEscuchando == null) {
            controladorEscuchando = new ControladorEscuchando();
        }

        controladorEscuchando.listener = new Listener();

        controladorEscuchando.listener.init("", controladorEscuchando.usuario.getPuerto(), false);

        controladorEscuchando.vistaEscuchando.setIpEscuchando(Usuario.getUsuario().getIp());
        controladorEscuchando.vistaEscuchando.mostrar();
        return controladorEscuchando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "volver":
                listener.pararEscucha();
                ControladorMainMenu.getControlador();
                vistaEscuchando.esconder();
                break;
        }
    }
}
