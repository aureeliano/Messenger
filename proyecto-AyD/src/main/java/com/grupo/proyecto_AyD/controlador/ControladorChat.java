package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.Conector;
import com.grupo.proyecto_AyD.negocio.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazChat;
import com.grupo.proyecto_AyD.vistas.VistaChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorChat implements ActionListener {
    private static ControladorChat controladorChat = null;
    private InterfazChat vistaChat;

    private Usuario usuario;
    private Conector conector;
    private Listener listener;

    private ControladorChat() {
        vistaChat = new VistaChat();
        vistaChat.setActionListener(this);
        usuario = Usuario.getUsuario();
    }

    public static ControladorChat getControlador(String ip, String puerto, boolean existeListener) {
        if (controladorChat == null) {
            controladorChat = new ControladorChat();
        }

        controladorChat.conector = new Conector();

        controladorChat.conector.init(ip, Integer.parseInt(puerto));

        if (!existeListener) {
            controladorChat.listener = Listener.getListener();
            controladorChat.listener.init("", Usuario.getUsuario().getPuerto());
        }

        controladorChat.vistaChat.mostrar();
        return controladorChat;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "enviar":
                if (vistaChat.getMensaje().equals("")) {
                    break;
                }
                usuario.getInterfazActiva().enviarMensaje(vistaChat.getMensaje());
                ControladorMainMenu.getControlador();
                vistaChat.esconder();
                break;
        }
    }
}
