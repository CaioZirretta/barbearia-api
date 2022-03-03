package com.barbearia.service.utils;

import lombok.Data;

@Data
public class EnderecoUtils {
	public static boolean validaEnderecoBR(String codigoPostal) {
		if (RequestExterno.requestEnderecoBR(codigoPostal).getCep() == null)
			return false;
		return true;
	}

	public static boolean validaEnderecoCA(String codigoPostal) {
		if (RequestExterno.requestEnderecoCA(codigoPostal).getPostal() == null)
			return false;
		return true;
	}
	
}
