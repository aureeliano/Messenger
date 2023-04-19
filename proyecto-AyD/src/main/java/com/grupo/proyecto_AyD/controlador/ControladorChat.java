package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.vistas.InterfazChat;
import com.grupo.proyecto_AyD.vistas.VistaChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorChat implements ActionListener {
    private static ControladorChat controladorChat = null;
    private InterfazChat vistaChat;

    private ControladorChat() {
        vistaChat = new VistaChat();
        vistaChat.setActionListener(this);
    }

    public static ControladorChat getControlador() {
        if (controladorChat == null) {
            controladorChat = new ControladorChat();
        }

        controladorChat.vistaChat.mostrar();
        return controladorChat;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
