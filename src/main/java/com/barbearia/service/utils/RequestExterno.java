package com.barbearia.service.utils;

import org.springframework.web.client.RestTemplate;

public class RequestExterno {
	
	private static RestTemplate restTemplate = new RestTemplate();

	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}
}