package com.barbearia.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Paises {
	BRASIL("BR"),
	CANADA("CA");
	
	private String sigla;
	
	Paises(String sigla){
		this.sigla = sigla;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public static Set<Paises> getListaPaises(){
		return EnumSet.allOf(Paises.class);
	}
}
