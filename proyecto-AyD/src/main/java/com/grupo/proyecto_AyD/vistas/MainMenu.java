package com.grupo.proyecto_AyD.vistas;

import javax.swing.*;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends JFrame implements InterfazMenu {

	private JPanel panel;
	private JButton btnIniciarConversacion; 
	private JButton btnIniciarEscucha;
	private JButton btnConfigurarPuerto;
	private JLabel txtpnMenuPrincipal;
	private JButton btnSalir;
	private JTextField textField;
	private JList<UsuarioDTO> listConectados;
	private JLabel lblNombre;
	private JLabel lblSuIp;
	private JLabel lblPuerto;
	private JTextField txtNombre;
	private JTextField txtIp;
	private JTextField txtPuerto;
	
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
		this.setBounds(100, 100, 756, 465);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel.setBackground(SystemColor.window);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		btnIniciarConversacion = new JButton("Iniciar Conversacion");
		btnIniciarConversacion.setActionCommand("iniciarConversacion");
		btnIniciarConversacion.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnIniciarConversacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnIniciarConversacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIniciarConversacion.setBounds(476, 111, 237, 54);
		panel.add(btnIniciarConversacion);
		
		btnIniciarEscucha = new JButton("Iniciar Escucha");
		btnIniciarEscucha.setActionCommand("iniciarEscucha");
		btnIniciarEscucha.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnIniciarEscucha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnIniciarEscucha.setBounds(476, 176, 237, 54);
		panel.add(btnIniciarEscucha);
		
		btnConfigurarPuerto = new JButton("Configurar");
		btnConfigurarPuerto.setActionCommand("configurar");
		btnConfigurarPuerto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConfigurarPuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfigurarPuerto.setBounds(476, 241, 237, 54);
		panel.add(btnConfigurarPuerto);
		
		txtpnMenuPrincipal = new JLabel();
		txtpnMenuPrincipal.setBackground(SystemColor.window);
		txtpnMenuPrincipal.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtpnMenuPrincipal.setText("Menu Principal");
		txtpnMenuPrincipal.setBounds(10, 11, 118, 28);
		panel.add(txtpnMenuPrincipal);
		
		btnSalir = new JButton("Salir");
		btnSalir.setActionCommand("salir");
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSalir.setBounds(476, 306, 237, 54);
		panel.add(btnSalir);
		
		listConectados = new JList();
		listConectados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listConectados.setBounds(10, 111, 436, 246);
		panel.add(listConectados);
		
		JLabel lblConectados = new JLabel("Usuarios online");
		lblConectados.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConectados.setBounds(10, 64, 436, 36);
		panel.add(lblConectados);
		
		JLabel lblEstadoActual = new JLabel();
		lblEstadoActual.setText("Estado:");
		lblEstadoActual.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstadoActual.setBackground(Color.WHITE);
		lblEstadoActual.setBounds(10, 387, 67, 28);
		panel.add(lblEstadoActual);
		
		textField = new JTextField();
		textField.setText("Inactivo");
		textField.setEditable(false);
		textField.setBounds(87, 387, 351, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		lblNombre = new JLabel("Su nombre");
		lblNombre.setBounds(379, 11, 67, 14);
		panel.add(lblNombre);
		
		lblSuIp = new JLabel("Su IP");
		lblSuIp.setBounds(476, 11, 67, 14);
		panel.add(lblSuIp);
		
		lblPuerto = new JLabel("Su Puerto");
		lblPuerto.setBounds(570, 11, 67, 14);
		panel.add(lblPuerto);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(379, 33, 86, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtIp = new JTextField();
		txtIp.setColumns(10);
		txtIp.setBounds(476, 33, 86, 20);
		panel.add(txtIp);
		
		txtPuerto = new JTextField();
		txtPuerto.setColumns(10);
		txtPuerto.setBounds(570, 33, 86, 20);
		panel.add(txtPuerto);
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

	@Override
	public void setEstado(String estado) {
		textField.setText(estado);
	}

	@Override
	public void setConectados(List<UsuarioDTO> usuarios) {
		listConectados.setListData(usuarios.toArray(new UsuarioDTO[usuarios.size()]));
	}

	@Override
	public UsuarioDTO getUsuarioSeleccionado() {
		return listConectados.getSelectedValue();
	}

	@Override
	public void cambiarBotonEscucha(boolean escuchando) {
		if (escuchando) {
			btnIniciarEscucha.setText("Detener Escucha");
		} else {
			btnIniciarEscucha.setText("Iniciar Escucha");
		}
	}

	@Override
	public void setIp(String ip) {
		txtIp.setText(ip);
	}

	@Override
	public void setPuerto(String puerto) {
		txtPuerto.setText(puerto);
	}

	@Override
	public void setNombre(String nombre) {
		txtNombre.setText(nombre);
	}
}
