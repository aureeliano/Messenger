package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.Conector;
import com.grupo.proyecto_AyD.negocio.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazChat;
import com.grupo.proyecto_AyD.vistas.VistaChat;
import lombok.Getter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorChat implements ActionListener {
    private static ControladorChat controladorChat = null;
    @Getter
    private InterfazChat vistaChat;

    private Usuario usuario;
    private Conector conector;
    private Listener listener;

    @Getter
    private boolean visible;

    private ControladorChat() {
        vistaChat = new VistaChat();
        vistaChat.setActionListener(this);
        usuario = Usuario.getUsuario();
    }

    public static ControladorChat getControlador(String ip, String puerto, boolean existeListener) {
        if (controladorChat == null) {
            controladorChat = new ControladorChat();
        }

        controladorChat.conector = Conector.getConector();

        controladorChat.conector.init(ip, Integer.parseInt(puerto), false);
        controladorChat.visible = true;

        if (!existeListener) {
            controladorChat.listener = Listener.getListener();
            controladorChat.listener.init("", Usuario.getUsuario().getPuerto(), true);
        }

        controladorChat.vistaChat.setIpCompañero(ip);

        controladorChat.vistaChat.mostrar();
        return controladorChat;
    }

    public static ControladorChat getControlador() {
        return controladorChat;
    }

    public void finalizarChat() {
        conector.manejarDesconexion();
        vistaChat.mostrarMensaje("El compañero ha finalizado la conversación");
        vistaChat.esconder();
        ControladorMainMenu.getControlador();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "enviar" -> {
                String mensaje = vistaChat.getMensaje();
                if (mensaje.equals("")) {
                    break;
                }
                List<Mensaje> mensajes = conector.enviarMensaje(mensaje);
                vistaChat.setMensajes(mensajes);
            }
            case "salir" -> {
                conector.finalizarConexion();
                ControladorMainMenu.getControlador();
                visible = false;
                vistaChat.esconder();
            }
        }
    }
}
