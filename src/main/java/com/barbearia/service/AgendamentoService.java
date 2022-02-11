package com.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.barbearia.model.Agendamento;
import com.barbearia.repository.AgendamentoRepository;
import com.barbearia.repository.ClienteRepository;
import com.barbearia.repository.PrestadorRepository;

@Service
public class AgendamentoService{
	
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PrestadorRepository prestadorRepository;
	
	public List<Agendamento> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	public Agendamento procurarPorCpfCliente() {
		// TODO Auto-generated method stub
		return null;
	}

	public Agendamento procurarPorCpfPrestador() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String deletarTudo() {
		return null;
	}

	public Agendamento agendar(Agendamento agendamento) {
		// TODO Auto-generated method stub
		return null;
	}
	
}