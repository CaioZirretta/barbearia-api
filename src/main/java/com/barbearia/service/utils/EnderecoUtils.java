package com.barbearia.service.utils;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;
import com.barbearia.service.factory.IEndereco;

public class EnderecoUtils {
	public static boolean validaEndereco(IEndereco endereco) {
		if (endereco instanceof EnderecoBR) {
			if (((EnderecoBR) endereco).getCep() == null)
				return false;
		}

		if (endereco instanceof EnderecoCA) {
			if (((EnderecoCA) endereco).getPostal() == null)
				return false;
		}
		
		return true;
	}
}
