package com.grupo.proyecto_AyD.vistas;

import com.grupo.proyecto_AyD.modelo.Mensaje;

import java.util.List;

public interface InterfazChat extends InterfazBase {
    String getMensaje();
    void setIpCompañero(String ip);

    void setMensajes(List<Mensaje> mensajes);
}
