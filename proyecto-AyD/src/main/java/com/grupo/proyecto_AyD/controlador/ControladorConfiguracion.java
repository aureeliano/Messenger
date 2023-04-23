package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.vistas.InterfazConfiguracion;
import com.grupo.proyecto_AyD.vistas.VistaConfiguracion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConfiguracion implements ActionListener {
    private static ControladorConfiguracion controladorConfiguracion = null;
    private InterfazConfiguracion vistaConfiguracion;
    private Usuario usuario;

    private ControladorConfiguracion() {
        vistaConfiguracion = new VistaConfiguracion();
        vistaConfiguracion.setActionListener(this);
        usuario = Usuario.getUsuario();
    }

    public static ControladorConfiguracion getControlador() {
        if (controladorConfiguracion == null) {
            controladorConfiguracion = new ControladorConfiguracion();
        }

        InterfazConfiguracion vista = controladorConfiguracion.vistaConfiguracion;
        Usuario usuario = controladorConfiguracion.usuario;
        vista.setPuerto(usuario.getPuerto());
        vista.setNombre(usuario.getNombre());

        controladorConfiguracion.vistaConfiguracion.mostrar();
        return controladorConfiguracion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "aceptar":
                usuario.actualizarInformacion(vistaConfiguracion.getNombre(), vistaConfiguracion.getPuerto());
                ControladorMainMenu.getControlador();
                vistaConfiguracion.esconder();
                break;
        }
    }
}
