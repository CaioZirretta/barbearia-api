package com.barbearia.service.utils;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import com.barbearia.model.EnderecoBR;
import com.barbearia.model.EnderecoCA;

public class RequestExterno {
	
	private static RestTemplate restTemplate = new RestTemplate();

	public static EnderecoBR requestEnderecoBR(String codigoPostal) {
		String url = "https://viacep.com.br/ws/" + codigoPostal + "/json/";
		EnderecoBR endBr = restTemplate.getForObject(url, EnderecoBR.class);
		return endBr;
	}

	public static EnderecoCA requestEnderecoCA(String codigoPostal) {
		
		String url = "https://geocoder.ca/" + codigoPostal + "?json=1";
		
		EnderecoCA endCA = restTemplate.getForObject(url, EnderecoCA.class);
		
		String jsonData = new String(restTemplate.getForObject(url, String.class));
		
		JSONObject jsonObject = new JSONObject(jsonData);
		
		endCA.setCity(jsonObject.getJSONObject("standard").getString("city"));
		endCA.setProv(jsonObject.getJSONObject("standard").getString("prov"));
		
		return endCA;
	}
}
