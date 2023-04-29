package com.grupo.proyecto_AyD.vistas;

import com.grupo.proyecto_AyD.modelo.Mensaje;
import lombok.Setter;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.util.List;

public class VistaChat extends JFrame implements InterfazChat {

	private JPanel contentPane;
	private JTextField textFieldIP;
	private JButton btnEnviar;
	private JTextField textFieldMensaje;
	private JLabel txtpnChateandoConIp;
	private JList<Mensaje> listMensajes;
	private ActionListener actionListener;
	private JButton btnSalir;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaChat frame = new VistaChat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaChat() {
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 296);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setActionCommand("enviar");
		btnEnviar.setBackground(SystemColor.text);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEnviar.setBounds(366, 229, 99, 32);
		contentPane.add(btnEnviar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setActionCommand("salir");
		btnSalir.setBounds(366, -4, 99, 32);
		contentPane.add(btnSalir);
		
		textFieldMensaje = new JTextField();
		textFieldMensaje.setForeground(SystemColor.activeCaptionBorder);
		textFieldMensaje.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMensaje.setToolTipText("Escriba su mensaje aqui...");
		textFieldMensaje.setBounds(0, 229, 367, 32);
		contentPane.add(textFieldMensaje);
		textFieldMensaje.setColumns(10);
		
		txtpnChateandoConIp = new JLabel();
		txtpnChateandoConIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnChateandoConIp.setText("Chateando con IP: ");
		txtpnChateandoConIp.setBounds(10, -3, 134, 26);
		contentPane.add(txtpnChateandoConIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(137, 0, 99, 26);
		contentPane.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		listMensajes = new JList<Mensaje>();
		listMensajes.setBounds(0, 27, 434, 210);
		contentPane.add(listMensajes);
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}

	@Override
	public void esconder() {
		this.setVisible(false);
	}

	@Override
	public void mostrar() {
		this.setVisible(true);
	}

	@Override
	public String getMensaje() {
		String mensaje = textFieldMensaje.getText();

		textFieldMensaje.setText("");

		return mensaje;
	}

	@Override
	public void setIpCompañero(String ip) {
		textFieldIP.setText(ip);
	}

	@Override
	public void setMensajes(List<Mensaje> mensajes) {
		listMensajes.setListData(mensajes.toArray(new Mensaje[0]));
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		this.btnEnviar.addActionListener(actionListener);
		this.btnSalir.addActionListener(actionListener);
	}
}
