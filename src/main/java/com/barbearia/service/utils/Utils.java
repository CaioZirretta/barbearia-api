package com.barbearia.service.utils;

import com.barbearia.model.dto.AnoMesDto;

public class Utils {
	public static boolean validaCpf(String cpf) {
		String cpfRegex = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})";

		if (cpf.isEmpty() || cpf.isBlank() || !cpf.matches(cpfRegex))
			return false;

		return true;
	}

	public static String formataCpf(String cpf) {
		cpf.trim();
		cpf.replace("-", "");
		cpf.replace("/", "");
		cpf.replace(".", "");
		return cpf;
	}
	
	public static boolean validaAnoMesDto(AnoMesDto anoMesDto) {
		// Valida o mês do DTO
		if (anoMesDto.getMes() < 1 || anoMesDto.getMes() > 12)
			return false;
		return true;
	}
}
