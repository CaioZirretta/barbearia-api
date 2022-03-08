package com.barbearia.service.factory;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;

public class EnderecoFactory {
	public static IEndereco enderecoFactory(String pais) {
		
		switch (pais) {
		case "BR":
			return new EnderecoBR();
		case "CA":
			return new EnderecoCA();
		default:
			return null;
		}
	}
}
