package com.grupo.proyecto_AyD.vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JList;

public class vistaChat extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIP;
	private JButton btnEnviar;
	private JTextField textFieldMensaje;
	private JTextPane txtpnChateandoConIp ;
	private JList listMensajes ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vistaChat frame = new vistaChat();
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
	public vistaChat() {
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		btnEnviar.setBounds(366, 229, 68, 32);
		contentPane.add(btnEnviar);
		
		textFieldMensaje = new JTextField();
		textFieldMensaje.setForeground(SystemColor.activeCaptionBorder);
		textFieldMensaje.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textFieldMensaje.setText("Escriba su mensaje aqui...");
		textFieldMensaje.setBounds(0, 229, 367, 32);
		contentPane.add(textFieldMensaje);
		textFieldMensaje.setColumns(10);
		
		txtpnChateandoConIp = new JTextPane();
		txtpnChateandoConIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnChateandoConIp.setText("Chateando con IP: ");
		txtpnChateandoConIp.setBounds(84, 0, 134, 26);
		contentPane.add(txtpnChateandoConIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(219, 0, 99, 26);
		contentPane.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		listMensajes = new JList();
		listMensajes.setBounds(0, 27, 434, 210);
		contentPane.add(listMensajes);
	}
}
