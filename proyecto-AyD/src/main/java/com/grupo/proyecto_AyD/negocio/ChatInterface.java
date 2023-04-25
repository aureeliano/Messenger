package com.grupo.proyecto_AyD.negocio;

public interface ChatInterface {

    void init(String ip, int puerto);

    void enviarMensaje(String mensaje);
}
