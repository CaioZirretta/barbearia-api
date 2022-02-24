package com.barbearia.service.factory.copy;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;
import com.barbearia.provider.IEndereco;

public class EnderecoFactory {
	String pais;
	
	public IEndereco enderecoProvider() {
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
