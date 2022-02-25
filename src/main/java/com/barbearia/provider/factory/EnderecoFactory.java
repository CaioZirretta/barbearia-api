package com.barbearia.provider.factory;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;
import com.barbearia.provider.IEndereco;

public class EnderecoFactory implements IEndereco {
	public IEndereco enderecoFactory(String pais) {
		switch (pais) {
		case "BR":
			return new EnderecoBR();
		case "CA":
			return new EnderecoCA();
		default:
			return new EnderecoBR();
		}
	}
}
