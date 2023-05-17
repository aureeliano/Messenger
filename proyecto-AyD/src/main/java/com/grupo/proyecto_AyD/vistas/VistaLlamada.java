package com.grupo.proyecto_AyD.vistas;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionListener;

public class VistaLlamada implements InterfazLlamada {

	private JFrame frame;
	private JTextField textFieldUsuario;
	private JTextField textFieldIp;

	private JButton btnAceptar;

	private JButton btnRechazar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaLlamada window = new VistaLlamada();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaLlamada() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChatEntrante = new JLabel("Chat Entrante");
		lblChatEntrante.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatEntrante.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblChatEntrante.setBounds(10, 11, 414, 48);
		frame.getContentPane().add(lblChatEntrante);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuario.setBounds(10, 90, 141, 14);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblIp = new JLabel("Direccion IP:");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIp.setBounds(10, 132, 141, 14);
		frame.getContentPane().add(lblIp);
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setEditable(false);
		textFieldUsuario.setBounds(161, 89, 263, 20);
		frame.getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		
		textFieldIp = new JTextField();
		textFieldIp.setEditable(false);
		textFieldIp.setColumns(10);
		textFieldIp.setBounds(161, 131, 263, 20);
		frame.getContentPane().add(textFieldIp);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(10, 227, 89, 23);
		btnAceptar.setActionCommand("aceptar");
		frame.getContentPane().add(btnAceptar);
		
		btnRechazar = new JButton("RECHAZAR");
		btnRechazar.setBounds(335, 227, 89, 23);
		btnRechazar.setActionCommand("rechazar");
		frame.getContentPane().add(btnRechazar);
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	@Override
	public void esconder() {
		frame.setVisible(false);
	}

	@Override
	public void mostrar() {
		frame.setVisible(true);
	}

	@Override
	public void setActionListener(ActionListener listener) {
		btnAceptar.addActionListener(listener);
		btnRechazar.addActionListener(listener);
	}

	@Override
	public void setUsuarioLlamada(String usuario) {
		textFieldUsuario.setText(usuario);
	}

	@Override
	public void setIpLlamada(String ip) {
		textFieldIp.setText(ip);
	}
}
