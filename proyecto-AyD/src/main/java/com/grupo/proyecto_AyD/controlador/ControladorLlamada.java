package com.grupo.proyecto_AyD.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.dtos.SolicitudLlamadaDTO;
import com.grupo.proyecto_AyD.red.Conector;
import com.grupo.proyecto_AyD.vistas.InterfazLlamada;
import com.grupo.proyecto_AyD.vistas.VistaLlamada;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class ControladorLlamada implements ActionListener {
    private static ControladorLlamada controladorLlamada = null;
    private InterfazLlamada vistaLlamada;
    private Conector conector;
    private SolicitudLlamadaDTO solicitud;

    private ObjectMapper mapper;

    private ControladorLlamada() {
        conector = Conector.getConector();
        vistaLlamada = new VistaLlamada();
        vistaLlamada.setActionListener(this);
        mapper = new ObjectMapper();
    }

    public static ControladorLlamada getControladorLlamada() {
        if (controladorLlamada == null) {
            controladorLlamada = new ControladorLlamada();
        }

        controladorLlamada.vistaLlamada.mostrar();
        return controladorLlamada;
    }

    public void setDatosSolicitud(SolicitudLlamadaDTO request) {
        solicitud = request;
        vistaLlamada.setUsuarioLlamada(request.getSolicitante().getNombre());
        vistaLlamada.setIpLlamada(request.getSolicitante().getIp());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "rechazar":
                try {
                    conector.enviarMensaje("[CONTROL][LLAMADA][RECHAZAR]" + mapper.writeValueAsString(solicitud));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                vistaLlamada.esconder();
                break;
            case "aceptar":
                try {
                    conector.enviarMensaje("[CONTROL][LLAMADA][ACEPTAR]" + mapper.writeValueAsString(solicitud));
                    conector.setClaveEncripcion(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
                    conector.enviarMensaje("[CONTROL][CLAVE]" + conector.getClaveEncripcion());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                vistaLlamada.esconder();
                ControladorEscuchando.getControlador().esconder();
                ControladorChat.getControlador(solicitud.getSolicitante().getIp(), true);
                break;
        }
    }
}
