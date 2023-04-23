package com.grupo.proyecto_AyD.vistas;

public interface InterfazConfiguracion extends InterfazBase{
    String getNombre();
    Integer getPuerto();

    void setNombre(String nombre);
    void setPuerto(Integer puerto);
}
