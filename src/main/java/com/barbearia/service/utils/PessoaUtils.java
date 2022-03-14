package com.barbearia.service.utils;

public class PessoaUtils {
	public static String formataCpf(String cpf) {
		return cpf.trim().replace("-", "").replace("/", "").replace(".", "");
	}
	
	public static boolean validaCpf(String cpf) {
		if (cpf == null || cpf.isEmpty() || cpf.isBlank() || !cpf.matches(RegexUtils.cpfRegex))
			return false;
		return true;
	}

	public static boolean validaAnoMes(Integer ano, Integer mes) {
		if (ano != null && mes != null)
			if (ano <= 12 || mes >= 1)
				return true;
		return false;
	}

	public static boolean validaNome(String nome) {
		if (nome == null || nome.isEmpty() || nome.isBlank() || nome.matches(RegexUtils.digitoNumericoRegex))
			return false;
		return true;
	}
}
