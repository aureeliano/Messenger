package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.vistas.InterfazConfiguracion;
import com.grupo.proyecto_AyD.vistas.VistaConfiguracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConfiguracion implements ActionListener {
    private static ControladorConfiguracion controladorConfiguracion = null;
    private InterfazConfiguracion vistaConfiguracion;

    private ControladorConfiguracion() {
        vistaConfiguracion = new VistaConfiguracion();
        vistaConfiguracion.setActionListener(this);
    }

    public static ControladorConfiguracion getControlador() {
        if (controladorConfiguracion == null) {
            controladorConfiguracion = new ControladorConfiguracion();
        }

        controladorConfiguracion.vistaConfiguracion.mostrar();
        return controladorConfiguracion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
