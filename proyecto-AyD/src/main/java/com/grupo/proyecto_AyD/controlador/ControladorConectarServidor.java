package com.grupo.proyecto_AyD.controlador;

import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.red.Listener;

public class ControladorConectarServidor {
    private static ControladorConectarServidor controladorConectarServidor = null;
    private Conector conector;
    private Listener listener;


    private ControladorConectarServidor() {
        conector = Conector.getConector();
        listener = Listener.getListener();
    }
}
