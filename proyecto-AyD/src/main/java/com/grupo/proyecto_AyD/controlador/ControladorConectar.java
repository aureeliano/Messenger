package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.Conector;
import com.grupo.proyecto_AyD.vistas.InterfazConectar;
import com.grupo.proyecto_AyD.vistas.VistaConectar;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConectar implements ActionListener {
    private static ControladorConectar controladorConectar = null;
    @Getter
    @Setter
    private Conector conector;
    private InterfazConectar vistaConectar;
    private Usuario usuario;

    private ControladorConectar() {
        vistaConectar = new VistaConectar();
        vistaConectar.setActionListener(this);
        usuario = Usuario.getUsuario();
    }

    public static ControladorConectar getControlador() {
        if (controladorConectar == null) {
            controladorConectar = new ControladorConectar();
        }

        controladorConectar.setConector(new Conector());

        controladorConectar.vistaConectar.mostrar();
        return controladorConectar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "volver":
                ControladorMainMenu.getControlador();
                vistaConectar.esconder();
                break;
            case "conectar":
                usuario.iniciarEscucha();
                conector.conectar(vistaConectar.getIp(), Integer.getInteger(vistaConectar.getPuerto()));
                ControladorEscuchando.getControlador();
                vistaConectar.esconder();
                break;
        }
    }
}
