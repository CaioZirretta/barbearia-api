package com.barbearia.service.utils;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnderecoUtils {
	public boolean validaEnderecoBR(EnderecoBR enderecoBR) {
		if (enderecoBR.getCep() == null)
			return false;
		return true;
	}

	public boolean validaEnderecoCA(EnderecoCA enderecoCA) {
		if (enderecoCA.getPostal() == null)
			return false;
		return true;
	}
	
	
	
}
