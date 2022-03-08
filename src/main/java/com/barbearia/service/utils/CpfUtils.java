package com.barbearia.service.utils;

public class CpfUtils {
	public static boolean validaCpf(String cpf) {
		if (cpf.isEmpty() || cpf.isBlank() || !cpf.matches(RegexUtils.cpfRegex))
			return false;
		return true;
	}

	public static String formataCpf(String cpf) {
		 return cpf.trim()
			.replace("-", "")
			.replace("/", "")
			.replace(".", "");
	}

	public static boolean validaAnoMes(Integer ano, Integer mes) {
		// Valida o mês do DTO
		if (ano > 12 || mes < 1)
			return false;
		return true;
	}
}
