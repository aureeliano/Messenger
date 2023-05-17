package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.Listener;
import com.grupo.proyecto_AyD.vistas.InterfazConectarServidor;
import com.grupo.proyecto_AyD.vistas.VistaConectarServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorConectarServidor implements ActionListener {
    private static ControladorConectarServidor controladorConectarServidor = null;
    private Conector conector;
    private Listener listener;
    private InterfazConectarServidor vistaConectarServidor;


    private ControladorConectarServidor() {
        conector = Conector.getConector();
        listener = Listener.getListener();
        listener.init("", Usuario.getUsuario().getPuerto(), false);
        conector.init("", 0, false);

        vistaConectarServidor = new VistaConectarServidor();
        vistaConectarServidor.setActionListener(this);
    }

    public static ControladorConectarServidor getControlador() {
        if (controladorConectarServidor == null) {
            controladorConectarServidor = new ControladorConectarServidor();
        }

        controladorConectarServidor.vistaConectarServidor.mostrar();

        return controladorConectarServidor;
    }

    public static void confirmarConexion() {
        ControladorMainMenu.getControlador();
        controladorConectarServidor.vistaConectarServidor.esconder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "conectar":
                try {
                    conector.enviarMensajeAServidor("[CONTROL][CONEXION_CLIENTE]", vistaConectarServidor.getIpServidor());
                } catch (Exception exception) {
                    exception.printStackTrace();
                    vistaConectarServidor.mostrarMensaje("No se pudo conectar con el servidor");
                }
                break;
            default:
                break;
        }
    }
}
