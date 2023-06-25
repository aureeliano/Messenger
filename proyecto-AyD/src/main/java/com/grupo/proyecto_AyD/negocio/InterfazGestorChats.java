package com.grupo.proyecto_AyD.negocio;

import com.grupo.proyecto_AyD.modelo.Mensaje;

public interface InterfazGestorChats {
    void cerrarChat();
    void confirmarConexion();
    void actualizarListaConectados(String lista);
    void enviarClaveDeEncriptacion(Mensaje mensaje);
}
