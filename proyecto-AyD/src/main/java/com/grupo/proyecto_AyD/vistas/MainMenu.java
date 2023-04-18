package com.grupo.proyecto_AyD.vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Cursor;

public class MainMenu {

	private JFrame frmMenuPrincipal;
	private JPanel panel;
	private JButton btnIniciarConversacion; 
	private JButton btnIniciarEscucha;
	private JButton btnConfigurarPuerto;
	private JTextPane txtpnMenuPrincipal;
	private JButton btnSalir; 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmMenuPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenuPrincipal = new JFrame();
		frmMenuPrincipal.setTitle("Menu Principal");
		frmMenuPrincipal.setBounds(100, 100, 458, 478);
		frmMenuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.setBackground(SystemColor.window);
		frmMenuPrincipal.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnIniciarConversacion = new JButton("Iniciar Conversacion");
		btnIniciarConversacion.setActionCommand("iniciarConversacion");
		btnIniciarConversacion.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnIniciarConversacion.setBackground(SystemColor.text);
		btnIniciarConversacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnIniciarConversacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIniciarConversacion.setBounds(27, 73, 388, 54);
		panel.add(btnIniciarConversacion);
		
		btnIniciarEscucha = new JButton("Iniciar Escucha");
		btnIniciarEscucha.setActionCommand("iniciarEscucha");
		btnIniciarEscucha.setBackground(SystemColor.text);
		btnIniciarEscucha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnIniciarEscucha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIniciarEscucha.setBounds(27, 161, 388, 54);
		panel.add(btnIniciarEscucha);
		
		btnConfigurarPuerto = new JButton("Configurar Puerto");
		btnConfigurarPuerto.setActionCommand("configurarPuerto");
		btnConfigurarPuerto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConfigurarPuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfigurarPuerto.setBounds(27, 249, 388, 54);
		panel.add(btnConfigurarPuerto);
		
		txtpnMenuPrincipal = new JTextPane();
		txtpnMenuPrincipal.setBackground(SystemColor.window);
		txtpnMenuPrincipal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtpnMenuPrincipal.setText("Menu Principal");
		txtpnMenuPrincipal.setBounds(152, 11, 121, 28);
		panel.add(txtpnMenuPrincipal);
		
		btnSalir = new JButton("Salir");
		btnSalir.setActionCommand("salir");
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(27, 346, 388, 54);
		panel.add(btnSalir);
	}
}
