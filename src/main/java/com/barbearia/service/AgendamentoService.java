package com.barbearia.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Agendamento;
import com.barbearia.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PrestadorService prestadorService;

	public List<Agendamento> listarTodos() {
		return agendamentoRepository.findAll();
	}

	public List<Agendamento> procurarPorCpfCliente(String cpfCliente) {
		clienteService.validaCpf(cpfCliente);
		clienteService.validaSeClienteNaoExiste(cpfCliente);
		return agendamentoRepository.findByCpfCliente(cpfCliente);
	}

	public List<Agendamento> procurarPorCpfPrestador(String cpfCliente) {
		prestadorService.validaCpf(cpfCliente);
		prestadorService.validaSePrestadorNaoExiste(cpfCliente);
		return agendamentoRepository.findByCpfCliente(cpfCliente);
	}

	public String deletarTudo() {
		JSONObject json = new JSONObject();
		json.put("message", "Registros apagados");

		agendamentoRepository.deleteAll();

		return json.toString();
	}

	public Agendamento agendar(Agendamento agendamento) {
		verificaHorarioCliente(agendamento);
		verificaHorarioPrestador(agendamento);
		
		return null;
	}
	
	private void verificaHorarioCliente(Agendamento agendamento) {
		if(agendamentoRepository.findHorarioByCliente(agendamento.getCpfCliente(), agendamento.getDia(), agendamento.getHorario()) != null)
			throw new ApiRequestException("Horário não disponível para o cliente.", HttpStatus.FORBIDDEN);
	}

	private void verificaHorarioPrestador(Agendamento agendamento) {
		if(agendamentoRepository.findHorarioByPrestador(agendamento.getCpfPrestador(), agendamento.getDia(), agendamento.getHorario()) != null)
			throw new ApiRequestException("Horário não disponível para o prestador.", HttpStatus.FORBIDDEN);
	}

}