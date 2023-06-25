package com.grupo.proyecto_AyD.encriptacion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Encriptacion implements Encriptador {
	public SecretKeySpec crearClave(String key) { //Crear clave de encriptacion/desencriptacion
		try {
			byte[] cadena = key.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			cadena = md.digest(cadena);
			cadena = Arrays.copyOf(cadena,16);
			SecretKeySpec secretKeySpec = new SecretKeySpec(cadena, "AES");
			return secretKeySpec;
		} catch (Exception e) {
			return null;
		}
	}
	
	public String encriptar(String encriptar, String key) {
		try {
			SecretKeySpec secretKeySpec = crearClave(key);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
			byte[] cadena = encriptar.getBytes(StandardCharsets.UTF_8);
			byte[] encriptada = cipher.doFinal(cadena);

			return Base64.getEncoder().encodeToString(encriptada);
			
		}
		catch(Exception e) {
			return ""; 
		}
	}
	
	public String desencriptar (String desencriptar, String key) {
		try {
			SecretKeySpec secretKeySpec = crearClave(key);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
			
			byte[] cadena = Base64.getDecoder().decode(desencriptar);
			byte[] desencriptada = cipher.doFinal(cadena);

			return new String(desencriptada);
		}
		catch(Exception e) {
			return ""; 
		}
	}
	
	
	
	
}