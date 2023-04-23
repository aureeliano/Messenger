package com.grupo.proyecto_AyD.vistas;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class VistaConectar extends JFrame implements InterfazConectar{

	private JTextField txtIP;
	private JTextField txtPuerto;
	private JButton btnConectar;
	private ActionListener actionListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaConectar window = new VistaConectar();
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
	public VistaConectar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 395, 288);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setActionCommand("conectar");
		btnConectar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnConectar.setBounds(126, 201, 113, 40);
		this.getContentPane().add(btnConectar);
		
		JLabel lblIp = new JLabel("Ingrese la IP de compañero");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblIp.setBounds(10, 10, 361, 33);
		this.getContentPane().add(lblIp);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtIP.setBounds(10, 53, 361, 33);
		this.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Ingrese el puerto de compañero");
		lblPuerto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPuerto.setBounds(10, 104, 361, 33);
		this.getContentPane().add(lblPuerto);
		
		txtPuerto = new JTextField();
		txtPuerto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPuerto.setColumns(10);
		txtPuerto.setBounds(10, 147, 361, 33);
		this.getContentPane().add(txtPuerto);
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		this.btnConectar.addActionListener(actionListener);
	}

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

	public String getIp() {
		return this.txtIP.getText();
	}

	public String getPuerto() {
		return this.txtPuerto.getText();
	}
}
