package com.barbearia.service.utils;

public class CpfUtils {
	public static boolean validaCpf(String cpf) {
		String cpfRegex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";

		if (cpf.isEmpty() || cpf.isBlank() || !cpf.matches(cpfRegex))
			return false;
		/*
		char dig10, dig11;
		int sm, i, resto, aux, peso;

		// Calculo do 1o. Digito Verificador
		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 é a posicao de '0' na tabela ASCII)
				aux = (int) (cpf.charAt(i) - 48);
				sm += (aux * peso);
				peso -= 1;
			}

			resto = 11 - (sm % 11);

			if ((resto == 10) || (resto == 11))
				dig10 = '0';
			else
				dig10 = (char) (resto + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				aux = (int) (cpf.charAt(i) - 48);
				sm += (aux * peso);
				peso -= 1;
			}

			resto = 11 - (sm % 11);
			if ((resto == 10) || (resto == 11))
				dig11 = '0';
			else
				dig11 = (char) (resto + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return true;
			else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}
		*/
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
