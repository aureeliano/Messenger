package com.grupo.proyecto_AyD.negocio;

import com.grupo.proyecto_AyD.modelo.Mensaje;

import java.util.List;

public interface InterfazGestorChats {
    void cerrarChat();
    void confirmarConexion();
    void actualizarListaConectados(String lista);
    void enviarClaveDeEncriptacion(Mensaje mensaje);
    void manejarSolicitudLlamada(String solicutudCruda);
    void manejarMensajeDeEstado(String estado);
    void mostrarNuevoMensaje(Mensaje mensaje);
    List<Mensaje> enviarMensaje(String contenido);
}
