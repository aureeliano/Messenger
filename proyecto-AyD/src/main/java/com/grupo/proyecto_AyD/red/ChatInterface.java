package com.grupo.proyecto_AyD.red;

import com.grupo.proyecto_AyD.modelo.Mensaje;

import java.util.List;

public interface ChatInterface {

    void init(String ip, int puerto, boolean desdeChat);

    List<Mensaje> enviarMensaje(String mensaje);
}
