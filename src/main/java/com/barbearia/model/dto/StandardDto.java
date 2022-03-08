package com.barbearia.model.dto;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class StandardDto {
	private String city;
	private String prov;
}
