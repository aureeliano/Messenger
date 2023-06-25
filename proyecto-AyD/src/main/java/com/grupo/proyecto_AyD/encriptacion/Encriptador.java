package com.grupo.proyecto_AyD.encriptacion;

import javax.crypto.spec.SecretKeySpec;

public interface Encriptador {
    SecretKeySpec crearClave(String key);
    String encriptar(String encriptar, String key);
    String desencriptar (String desencriptar, String key);
}
