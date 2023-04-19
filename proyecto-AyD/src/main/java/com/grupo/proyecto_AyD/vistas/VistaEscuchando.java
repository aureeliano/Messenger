package com.grupo.proyecto_AyD.vistas;

import lombok.Setter;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class VistaEscuchando extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIP;
	private String ipEscuchando;
	@Setter
	private ActionListener actionListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaEscuchando frame = new VistaEscuchando();
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
	public VistaEscuchando() {
		setTitle("Escuchando");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnEscuchando = new JTextPane();
		txtpnEscuchando.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnEscuchando.setText("Escuchando...");
		txtpnEscuchando.setToolTipText("");
		txtpnEscuchando.setBounds(154, 11, 99, 25);
		contentPane.add(txtpnEscuchando);
		
		JTextPane txtpnSuIpEs = new JTextPane();
		txtpnSuIpEs.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnSuIpEs.setToolTipText("");
		txtpnSuIpEs.setText("Su IP es: ");
		txtpnSuIpEs.setBounds(120, 42, 76, 20);
		contentPane.add(txtpnSuIpEs);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(198, 42, 86, 20);
		contentPane.add(textFieldIP);
		textFieldIP.setColumns(10);
	}

}
