package com.barbearia.model;

import java.util.List;

import com.barbearia.provider.IEndereco;

public class EnderecoCA implements IEndereco{
	List<String> standard;	
	List<String> disseminationArea;
	String longt;
	String postal;
	String latt;
}
