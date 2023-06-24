package com.grupo.proyecto_AyD.red;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo.proyecto_AyD.dtos.ExtremoDTO;
import com.grupo.proyecto_AyD.dtos.SolicitudLlamadaDTO;
import com.grupo.proyecto_AyD.encriptacion.Encriptacion;
import com.grupo.proyecto_AyD.modelo.Mensaje;
import com.grupo.proyecto_AyD.modelo.Sesion;
import com.grupo.proyecto_AyD.modelo.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

public class Conector implements ChatInterface  {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectMapper mapper;
    private Usuario usuario;
    private String targetIp;
    private int targetPuerto;
    private String ipServidor;
    private int puertoServidor;

    private static Conector conector;

    @Getter
    @Setter
    private String claveEncripcion;

    private Encriptacion encriptacion = new Encriptacion();


    public void init(String ip, int puerto, boolean desdeChat) {
        this.usuario = Usuario.getUsuario();

        try {
            usuario.setIp(InetAddress.getLocalHost().getHostAddress());
            this.targetIp = ip;
            this.targetPuerto = puerto;

            mapper = new ObjectMapper();
        } catch (IOException e) {
            System.out.println("Error al iniciar el conector: " + e.getMessage());
        }
    }

    @Override
    public List<Mensaje> enviarMensaje(String contenido) {
        int retry = 0;
        int maxRetry = 50;
        boolean exito = false;

        while (!exito && retry < maxRetry) {
            try {
                System.out.println("Intentando enviar mensaje: " + contenido);
                socket = new Socket(targetIp, targetPuerto);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                if (!contenido.contains("[CONTROL]")) {
                    Mensaje mensaje = new Mensaje(null);

                    Mensaje mensajeEncriptado = new Mensaje(contenido);
                    mensaje.setContenidoEncriptado(encriptacion.encriptar(mapper.writeValueAsString(mensajeEncriptado), claveEncripcion));

                    Sesion.getSesion().getMensajes().add(mensajeEncriptado);

                    out.println(mapper.writeValueAsString(mensaje));
                } else {
                    Mensaje mensaje = new Mensaje(contenido);

                    out.println(mapper.writeValueAsString(mensaje));
                }

                out.flush();
                out.close();
                System.out.println("Mensaje enviado: " + contenido);
                exito = true;

                return Sesion.getSesion().getMensajes()
                        .stream()
                        .sorted(Comparator.comparing(Mensaje::getFecha))
                        .toList();
            } catch (Exception e) {
                System.out.println("Error enviando mensaje, reintentando: " + e.getMessage());
                retry++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        return Sesion.getSesion().getMensajes()
                .stream()
                .sorted(Comparator.comparing(Mensaje::getFecha))
                .toList();
    }

    //Este metodo se usa en caso de que el usuario se desconecte
    public void finalizarConexion() {
        try {
            enviarMensaje("[CONTROL][FINALIZAR_CHAT]");
            manejarDesconexion();
        } catch (Exception e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
    }

    //Este metodo se usa en caso de que el compañero se desconecte
    public void manejarDesconexion() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el socket: " + e.getMessage());
        }
    }



    public static Conector getConector() {
        if (conector == null) {
            conector = new Conector();
        }

        return conector;
    }

    public void enviarMensajeAServidor(String mensaje, String ip) {
        this.targetIp = ip;
        this.targetPuerto = 3001;
        this.puertoServidor = 3001;
        this.ipServidor = ip;

        enviarMensaje(mensaje);
    }

    public void iniciarChat(String ip, int puerto) {
        this.targetIp = ipServidor;
        this.targetPuerto = puertoServidor;
        this.usuario = Usuario.getUsuario();
        mapper = new ObjectMapper();

        SolicitudLlamadaDTO solicitudLlamadaDTO = new SolicitudLlamadaDTO();
        ExtremoDTO solicitante = new ExtremoDTO();
        ExtremoDTO destino = new ExtremoDTO();

        solicitante.setIp(usuario.getIp());
        solicitante.setPuerto(usuario.getPuerto());
        solicitante.setNombre(usuario.getNombre());
        destino.setIp(ip);
        destino.setPuerto(puerto);

        solicitudLlamadaDTO.setSolicitante(solicitante);
        solicitudLlamadaDTO.setDestino(destino);

        enviarMensaje("[CONTROL][CONECTAR]" + mapper.valueToTree(solicitudLlamadaDTO).toString());
    }
}
