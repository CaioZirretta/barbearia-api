package com.barbearia.service.utils;

import com.barbearia.enums.Paises;

public class EnderecoUtils {

	public static boolean validaCodigoPostal(String codigoPostal) {
		if (!(codigoPostal == null))
			if (codigoPostal.matches(RegexUtils.brPostalRegex) || codigoPostal.matches(RegexUtils.caPostalRegex))
				return true;
		return false;
	}

	public static boolean validaComplemento(String complemento) {
		if (complemento == null || complemento.length() > 50)
			return false;
		return true;
	}

	public static String paisOrigem(String origem) {
		for (Paises pais : Paises.getListaPaises()) {
			if (origem.equalsIgnoreCase(pais.getSigla()))
				return pais.toString();
		}
		return null;
	}

	public static boolean validaOrigem(String origem) {
		if(origem == null)
			return false;
		return true;
	}

}
