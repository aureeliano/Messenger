package com.grupo.proyecto_AyD.vistas;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

public class VistaConectarServidor extends JFrame implements InterfazConectarServidor {
	private JTextField txtIPServidor;
	private JButton btnConectar;
	private JLabel lblNombre;
	private JTextField txtNombre;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaConectarServidor window = new VistaConectarServidor();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaConectarServidor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 448, 217);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblServidor = new JLabel("Ingrese la IP del servidor");
		lblServidor.setBounds(0, 0, 434, 20);
		lblServidor.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblServidor.setAlignmentY(0.1f);
		lblServidor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblServidor.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblServidor);
		
		txtIPServidor = new JTextField();
		txtIPServidor.setBounds(128, 32, 178, 20);
		getContentPane().add(txtIPServidor);
		txtIPServidor.setColumns(10);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setActionCommand("conectar");
		btnConectar.setBounds(175, 144, 89, 23);
		getContentPane().add(btnConectar);
		
		lblNombre = new JLabel("Ingrese un nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNombre.setAlignmentY(0.1f);
		lblNombre.setAlignmentX(0.5f);
		lblNombre.setBounds(0, 71, 434, 20);
		getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(128, 102, 178, 20);
		getContentPane().add(txtNombre);
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
	public void setActionListener(ActionListener actionListener) {
		btnConectar.addActionListener(actionListener);
	}


	@Override
	public String getIpServidor() {
		return this.txtIPServidor.getText();
	}
	
	@Override
	public String getNombre() {
		return this.txtNombre.getText();
	}
}
