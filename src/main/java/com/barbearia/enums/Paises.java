package com.barbearia.enums;

import java.util.EnumSet;
import java.util.Set;

public enum Paises {
	BRASIL("BR"),
	CANADA("CA");
	
	private String pais;
	
	Paises(String pais){
		this.pais = pais;
	}
	
	public String getPais() {
		return pais;
	}
	
	public static Set<Paises> todos(){
		return EnumSet.allOf(Paises.class);
	}
}
