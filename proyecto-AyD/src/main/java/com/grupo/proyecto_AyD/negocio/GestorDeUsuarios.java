package com.grupo.proyecto_AyD.negocio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.controlador.ControladorServidor;
import com.grupo.proyecto_AyD.dtos.UsuarioDTO;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Servidor;
import com.grupo.proyecto_AyD.modelo.Usuario;
import com.grupo.proyecto_AyD.red.ConectorServidor;

import java.util.List;

public class GestorDeUsuarios implements InterfazGestorUsuarios {
    private final ConectorServidor conectorServidor;
    private final Servidor servidor;
    private final ControladorServidor controladorServidor;
    private final ObjectMapper mapper;

    public GestorDeUsuarios(ConectorServidor conectorServidor, ControladorServidor controladorServidor) {
        this.conectorServidor = conectorServidor;
        this.servidor = Servidor.getServidor();
        this.controladorServidor = controladorServidor;
        this.mapper = new ObjectMapper();
    }

    public void manejarConexion(Mensaje mensaje) {
        if (!this.servidor.getUsuariosConectados().contains(mensaje.getRemitente())) {
            this.servidor.getUsuariosConectados().add(mensaje.getRemitente());
            controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());
            notificarConectados();

            System.out.println("[SERVIDOR] Usuario conectado: " + mensaje.getRemitente());
            this.conectorServidor.enviarMensaje(mensaje.getRemitente(),"[CONTROL]IP:" + servidor.getIp());
            this.conectorServidor.enviarMensaje(mensaje.getRemitente(), "[CONTROL]PUERTO:3001");
            this.conectorServidor.enviarMensaje(mensaje.getRemitente(), "[CONTROL][CONEXION_CLIENTE][OK]");
        }
    }

    public void manejarDesconexion(Mensaje mensaje) {
        Usuario remitente = mensaje.getRemitente();
        quitarUsuario(remitente);
        controladorServidor.actualizarConectados(servidor.getUsuariosConectados().stream().toList());
        conectorServidor.sincronizar();

        notificarConectados();

        System.out.println("[SERVIDOR] Usuario desconectado: " + mensaje.getRemitente());
    }

    public void quitarUsuario(Usuario remitente) {
        this.servidor.getUsuariosConectados().removeIf(u -> u.getIp().equals(remitente.getIp()) && u.getPuerto() == remitente.getPuerto());
    }

    public void notificarConectados() {
        List<UsuarioDTO> conectados = servidor.getUsuariosConectados().stream().map(UsuarioDTO::fromUsuario).toList();

        servidor
                .getUsuariosConectados()
                .forEach(u -> {
                            try {
                                conectorServidor.enviarMensaje(u, "[CONTROL][CONECTADOS]" + mapper.writeValueAsString(conectados));
                            } catch (JsonProcessingException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                );
    }
}
