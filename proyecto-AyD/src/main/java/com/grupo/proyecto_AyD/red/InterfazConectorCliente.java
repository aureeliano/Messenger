package com.grupo.proyecto_AyD.red;

public interface InterfazConectorCliente {
    void iniciarChat(String ip, int puerto);
    void init(String ip, int puerto, boolean desdeChat);
    void enviarMensajeAServidor(String mensaje, String ip);
}
