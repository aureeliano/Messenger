package com.grupo.proyecto_AyD.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements InterfazBase {

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
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setTitle("Menu Principal");
		this.setBounds(100, 100, 458, 478);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.setBackground(SystemColor.window);
		this.getContentPane().add(panel, BorderLayout.CENTER);
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
		
		btnConfigurarPuerto = new JButton("Configurar");
		btnConfigurarPuerto.setActionCommand("configurar");
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
		txtpnMenuPrincipal.setBounds(157, 10, 125, 28);
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

	public void setActionListener(ActionListener actionListener) {
		btnIniciarConversacion.addActionListener(actionListener);
		btnIniciarEscucha.addActionListener(actionListener);
		btnConfigurarPuerto.addActionListener(actionListener);
		btnSalir.addActionListener(actionListener);
	}
}
