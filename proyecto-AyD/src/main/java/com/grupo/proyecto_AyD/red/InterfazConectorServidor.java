package com.grupo.proyecto_AyD.red;

import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Usuario;

public interface InterfazConectorServidor {
    void init();
    void enviarMensaje(Usuario usuario, String contenido);
    void reenviarMensaje(Usuario usuario, Mensaje mensaje);
    void sincronizar();
    void setListenerServidor(ListenerServidor listenerServidor);
}
