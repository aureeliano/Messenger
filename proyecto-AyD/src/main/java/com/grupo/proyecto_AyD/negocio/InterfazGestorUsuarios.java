package com.grupo.proyecto_AyD.negocio;

import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Usuario;

public interface InterfazGestorUsuarios {
    void manejarConexion(Mensaje mensaje);
    void manejarDesconexion(Mensaje mensaje);
    void quitarUsuario(Usuario remitente);
    void notificarConectados();
}
