package com.grupo.proyecto_AyD.negocio;

public interface ChatInterface {

    void init(String ip, int puerto, boolean desdeChat);

    void enviarMensaje(String mensaje);
}
