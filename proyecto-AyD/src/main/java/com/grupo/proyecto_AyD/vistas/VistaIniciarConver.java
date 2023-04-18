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

public class VistaIniciarConver extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIP;
	private JButton btnConectar; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaIniciarConver frame = new VistaIniciarConver();
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
	public VistaIniciarConver() {
		setTitle("Iniciar Conversacion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 459, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnIngreseIpQue = new JTextPane();
		txtpnIngreseIpQue.setBounds(121, 11, 222, 34);
		txtpnIngreseIpQue.setText("Ingrese IP que esta escuchando");
		txtpnIngreseIpQue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnIngreseIpQue.setBackground(SystemColor.menu);
		contentPane.add(txtpnIngreseIpQue);
		
		textFieldIP = new JTextField();
		textFieldIP.setColumns(10);
		textFieldIP.setBounds(44, 56, 378, 29);
		contentPane.add(textFieldIP);
		
		btnConectar = new JButton("Conectar");
		btnConectar.setActionCommand("conectar");
		btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConectar.setBounds(154, 104, 136, 29);
		contentPane.add(btnConectar);
	}

}
