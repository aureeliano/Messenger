package com.grupo.proyecto_AyD.vistas;

import java.awt.event.ActionListener;

public interface InterfazBase {
    void mostrarMensaje(String mensaje);
    void esconder();
    void mostrar();

    void setActionListener(ActionListener listener);
}
