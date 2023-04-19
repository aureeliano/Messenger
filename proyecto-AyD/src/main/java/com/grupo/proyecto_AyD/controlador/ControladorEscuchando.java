package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.vistas.InterfazEscuchando;
import com.grupo.proyecto_AyD.vistas.VistaEscuchando;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEscuchando implements ActionListener {
    private static ControladorEscuchando controladorEscuchando = null;
    private InterfazEscuchando vistaEscuchando;

    private ControladorEscuchando() {
        vistaEscuchando = new VistaEscuchando();
        vistaEscuchando.setActionListener(this);
    }

    public static ControladorEscuchando getControlador() {
        if (controladorEscuchando == null) {
            controladorEscuchando = new ControladorEscuchando();
        }

        controladorEscuchando.vistaEscuchando.mostrar();
        return controladorEscuchando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
