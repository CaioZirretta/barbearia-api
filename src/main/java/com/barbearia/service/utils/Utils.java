package com.barbearia.service.utils;

import java.util.ArrayList;

public class Utils {
	public static boolean validaCpf(String cpf) {
		String cpfRegex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";

		if (cpf.isEmpty() || cpf.isBlank() || !cpf.matches(cpfRegex))
			return false;
		
		ArrayList<Integer> cpfArray = new ArrayList<Integer>();
		
		for (int i = 0; i < cpf.length(); i++) {
			cpfArray.add(Integer.parseInt(cpf.substring(i, i+1)));
		}
		
		
		return true;
	}

	public static String formataCpf(String cpf) {
		cpf.trim();
		cpf.replace("-", "");
		cpf.replace("/", "");
		cpf.replace(".", "");
		return cpf;
	}
	
	public static boolean validaAnoMes(Integer ano, Integer mes) {
		// Valida o mês do DTO
		if (ano > 12 || mes < 1)
			return false;
		return true;
	}
}
