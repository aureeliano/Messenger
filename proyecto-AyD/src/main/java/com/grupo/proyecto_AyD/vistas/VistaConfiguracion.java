package com.grupo.proyecto_AyD.vistas;

import lombok.Setter;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

public class VistaConfiguracion extends JFrame implements InterfazConfiguracion {

	private JPanel contentPane;
	private JTextField textFieldPuerto;
	private JButton btnAceptar;
	private JLabel txtpnIngreseUnPuerto;
	private JLabel txtNombre;
	private JTextField textFieldNombre;
	private ActionListener actionListener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaConfiguracion frame = new VistaConfiguracion();
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
	public VistaConfiguracion() {
		setTitle("Configuracion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 466, 272);
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
		btnAceptar.setBounds(161, 187, 136, 29);
		contentPane.add(btnAceptar);
		
		txtpnIngreseUnPuerto = new JLabel();
		txtpnIngreseUnPuerto.setBackground(SystemColor.control);
		txtpnIngreseUnPuerto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnIngreseUnPuerto.setText("Ingrese un puerto para escuchar. EJ: 8080");
		txtpnIngreseUnPuerto.setBounds(36, 22, 297, 29);
		contentPane.add(txtpnIngreseUnPuerto);
		
		txtNombre = new JLabel();
		txtNombre.setText("Ingrese un nickname");
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNombre.setBackground(SystemColor.menu);
		txtNombre.setBounds(36, 91, 297, 29);
		contentPane.add(txtNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(36, 119, 378, 29);
		contentPane.add(textFieldNombre);
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
	public String getNombre() {
		return textFieldNombre.getText();
	}

	public void setNombre(String nombre) {
		textFieldNombre.setText(nombre);
	}

	@Override
	public Integer getPuerto() {
		return Integer.valueOf(textFieldPuerto.getText());
	}

	public void setPuerto(Integer puerto) {
		textFieldPuerto.setText(puerto.toString());
	}

	@Override
	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
		btnAceptar.addActionListener(actionListener);
	}
}
