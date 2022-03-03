package com.barbearia.model;

import javax.persistence.Embeddable;

import com.barbearia.service.factory.IEndereco;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnderecoCA implements IEndereco {
	String city;
	String prov;
	String stnumber;
	String staddress;
	String postal;
}
