package com.totvs.barbearia.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbearia.BarbeariaApiApplication;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.dto.EnderecoDto;
import com.barbearia.model.dto.NovaPessoaDto;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;
import com.barbearia.service.ClienteService;
import com.barbearia.service.factory.IEndereco;

@SpringBootTest(classes = BarbeariaApiApplication.class)
public class ClienteServiceTest {

	@Mock
	private IEndereco iEndereco;

	@Autowired
	@InjectMocks
	private ClienteService clienteService;

	@Autowired
	@Mock
	private PrestadorRepository prestadorRepository;
	
	@Autowired
	@Mock
	private ClienteRepository clienteRepository;

	// Adicionar

	@Test
	public void adicionar_falhaCodigoPostalNulo() {
		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(new NovaPessoaDto());
		});
	}

	@Test
	public void adicionar_falhaComplementoNulo() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}

	@Test
	public void adicionar_falhaOrigemNulo() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");
		novaPessoaDto.setComplemento("complemento");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}

	@Test
	public void adicionar_falhaCpfNulo() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");
		novaPessoaDto.setComplemento("complemento");
		novaPessoaDto.setOrigem("BR");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}

	@Test
	public void adicionar_falhaNomeNulo() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");
		novaPessoaDto.setComplemento("complemento");
		novaPessoaDto.setOrigem("BR");
		novaPessoaDto.setCpf("93888053480");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}

	@Test
	public void adicionar_falhaPrestadorComMesmoCpf() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");
		novaPessoaDto.setComplemento("complemento");
		novaPessoaDto.setOrigem("BR");
		novaPessoaDto.setCpf("93888053480");
		novaPessoaDto.setNome("Teste");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}
	
	@Test
	public void adicionar_falhaClienteJaExiste() {
		NovaPessoaDto novaPessoaDto = new NovaPessoaDto();
		novaPessoaDto.setCodigoPostal("74815435");
		novaPessoaDto.setComplemento("complemento");
		novaPessoaDto.setOrigem("BR");
		novaPessoaDto.setCpf("35842224941");
		novaPessoaDto.setNome("Poste");

		Mockito.when(iEndereco.requestEndereco(Mockito.anyString())).thenReturn(new EnderecoDto());

		Assertions.assertEquals(clienteService.adicionar(novaPessoaDto), clienteRepository.findByCpf(novaPessoaDto.getCpf()));
	
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.adicionar(novaPessoaDto);
		});
	}
	

	// Listar

	@Test
	public void listarTodos_falhaTabelaNula() {
		Assertions.assertThrows(ApiRequestException.class, () -> {
			clienteService.listarTodos();
		});
	}

}
