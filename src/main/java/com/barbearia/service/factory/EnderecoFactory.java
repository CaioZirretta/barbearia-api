package com.barbearia.service.factory;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;

public class EnderecoFactory {
	public static IEndereco enderecoFactory(String codigoPostal) {
		final Integer codigoPostalLength = codigoPostal.length();
		
		switch (codigoPostalLength) {
		case 5:
			return new EnderecoCA();
		case 8:
			return new EnderecoBR();
		default:
			return null;
		}
	}
}
