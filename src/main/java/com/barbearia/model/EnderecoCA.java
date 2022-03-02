package com.barbearia.model;

import java.util.HashMap;

import com.barbearia.service.factory.IEndereco;

import lombok.Data;

@Data
public class EnderecoCA implements IEndereco{

	String city;
	String prov;
	String stnumber;
	String staddress;
	String postal;
	
}
