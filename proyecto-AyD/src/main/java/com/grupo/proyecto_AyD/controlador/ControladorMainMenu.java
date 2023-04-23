package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.vistas.InterfazBase;
import com.grupo.proyecto_AyD.vistas.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorMainMenu implements ActionListener {
    private static ControladorMainMenu controladorMainMenu = null;
    private InterfazBase vistaMenu;
    private Usuario usuario;

    private ControladorMainMenu() {
        vistaMenu = new MainMenu();
        vistaMenu.setActionListener(this);
        usuario = Usuario.getUsuario();
    }

    public static ControladorMainMenu getControlador() {
        if (controladorMainMenu == null) {
            controladorMainMenu = new ControladorMainMenu();
        }

        controladorMainMenu.vistaMenu.mostrar();
        return controladorMainMenu;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "iniciarEscucha":
                usuario.iniciarEscucha();
                ControladorEscuchando.getControlador();
                vistaMenu.esconder();
                break;
            case "configurar":
                ControladorConfiguracion.getControlador();
                vistaMenu.esconder();
                break;
            case "iniciarConversacion":
                ControladorChat.getControlador();
                vistaMenu.esconder();
                break;
            case "salir":
                System.exit(0);
                break;
        }

    }
}
