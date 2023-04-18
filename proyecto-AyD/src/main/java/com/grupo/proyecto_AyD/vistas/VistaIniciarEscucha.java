package com.grupo.proyecto_AyD.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.SystemColor;

public class VistaIniciarEscucha extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPuerto;
	private JButton btnAceptar;
	private JTextPane txtpnIngreseUnPuerto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaIniciarEscucha frame = new VistaIniciarEscucha();
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
	public VistaIniciarEscucha() {
		setTitle("Iniciar Escucha");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 186);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setBounds(36, 51, 378, 29);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setActionCommand("aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAceptar.setBounds(161, 97, 136, 29);
		contentPane.add(btnAceptar);
		
		txtpnIngreseUnPuerto = new JTextPane();
		txtpnIngreseUnPuerto.setBackground(SystemColor.control);
		txtpnIngreseUnPuerto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnIngreseUnPuerto.setText("Ingrese un puerto para escuchar");
		txtpnIngreseUnPuerto.setBounds(111, 11, 222, 29);
		contentPane.add(txtpnIngreseUnPuerto);
	}

}
