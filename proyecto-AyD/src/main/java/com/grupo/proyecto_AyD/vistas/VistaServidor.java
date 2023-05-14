package com.grupo.proyecto_AyD.vistas;

import com.grupo.proyecto_AyD.dtos.UsuarioDTO;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaServidor extends JFrame implements InterfazServidor {
	private JPanel contentPane;
	private JTextField txtFieldIp;

	private JList<UsuarioDTO> listUsuarios;
	private JButton btnSalir;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaServidor window = new VistaServidor();
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
	public VistaServidor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCorriendo = new JLabel("Corriendo Servidor en:");
		lblCorriendo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCorriendo.setBounds(10, 11, 146, 26);
		contentPane.add(lblCorriendo);
		
		txtFieldIp = new JTextField();
		txtFieldIp.setEnabled(false);
		txtFieldIp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFieldIp.setBounds(166, 16, 146, 20);
		contentPane.add(txtFieldIp);
		txtFieldIp.setColumns(10);
		
		JLabel lblConectados = new JLabel("Usuarios Conectados:");
		lblConectados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConectados.setBounds(10, 55, 146, 14);
		contentPane.add(lblConectados);
		
		listUsuarios = new JList<UsuarioDTO>();
		listUsuarios.setEnabled(false);
		listUsuarios.setBounds(10, 220, 414, -139);
		contentPane.add(listUsuarios);
		
		btnSalir = new JButton("Cerrar Servidor");
		btnSalir.setActionCommand("salir");
		btnSalir.setBounds(10, 227, 146, 23);
		contentPane.add(btnSalir);
	}

	@Override
	public void mostrarMensaje(String mensaje) {

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
	public void setActionListener(ActionListener listener) {
		btnSalir.addActionListener(listener);
	}

	@Override
	public void setUsuariosConectados(List<UsuarioDTO> usuariosConectados) {
		listUsuarios.setListData(usuariosConectados.toArray(new UsuarioDTO[0]));
	}

	@Override
	public void setIpServidor(String ipServidor) {
		txtFieldIp.setText(ipServidor);
	}
}
