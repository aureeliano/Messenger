package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.vistas.InterfazBase;
import com.grupo.proyecto_AyD.vistas.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorMainMenu implements ActionListener {
    private static ControladorMainMenu controladorMainMenu = null;
    private InterfazBase vistaMenu;

    private ControladorMainMenu() {
        vistaMenu = new MainMenu();
        vistaMenu.setActionListener(this);
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

    }
}
