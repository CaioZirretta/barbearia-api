package com.totvs.barbearia.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.repository.PrestadorRepository;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class PrestadorRepositoryTest {
	
	@Autowired
	@Mock
	private PrestadorRepository prestadorRepository;
	
	
}
