package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.negocio.GestorDeChats;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazEscuchando;
import com.grupo.proyecto_AyD.vistas.VistaEscuchando;

import javax.naming.ldap.Control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEscuchando implements ActionListener {
    private static ControladorEscuchando controladorEscuchando = null;
    private InterfazEscuchando vistaEscuchando;
    private Usuario usuario;
    private GestorDeChats gestorDeChats;

    private ControladorEscuchando() {
        vistaEscuchando = new VistaEscuchando();
        usuario = Usuario.getUsuario();
        gestorDeChats = GestorDeChats.getGestor();
        vistaEscuchando.setActionListener(this);
    }

    public static ControladorEscuchando getControlador() {
        if (controladorEscuchando == null) {
            controladorEscuchando = new ControladorEscuchando();
        }

        controladorEscuchando.gestorDeChats.enviarMensaje("[CONTROL][ESCUCHANDO][INICIAR]");

        controladorEscuchando.vistaEscuchando.setIpEscuchando(Usuario.getUsuario().getIp());
        controladorEscuchando.vistaEscuchando.setPuerto(Usuario.getUsuario().getPuerto());
        controladorEscuchando.vistaEscuchando.mostrar();
        return controladorEscuchando;
    }

    public static ControladorEscuchando getSilenciosamente() {
        if (controladorEscuchando == null) {
            controladorEscuchando = new ControladorEscuchando();
        }

        return controladorEscuchando;
    }

    public void esconder() {
        vistaEscuchando.esconder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "volver":
                gestorDeChats.enviarMensaje("[CONTROL][ESCUCHANDO][TERMINAR]");
                ControladorMainMenu.getControlador();
                vistaEscuchando.esconder();
                break;
        }
    }
}
