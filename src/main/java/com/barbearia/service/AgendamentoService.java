package com.barbearia.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Agendamento;
import com.barbearia.model.EnderecoBR;
import com.barbearia.repository.AgendamentoRepository;
import com.barbearia.service.utils.Utils;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PrestadorService prestadorService;
	
	private static final LocalTime horarioInicio = LocalTime.of(8, 0);
	private static final LocalTime horarioFim = LocalTime.of(18, 0);
	private static final int intervaloDias = 1;
	private static final int intervaloHoras = 1;

	public List<Agendamento> listarTodos() {
		if (agendamentoRepository.findCount() == 0)
			throw new ApiRequestException("Não existem agendamentos marcados");
		return agendamentoRepository.findAll();
	}

	public List<LocalDate> listarHorarioVagoMes(Integer ano, Integer mes) {

		if (!Utils.validaAnoMes(ano, mes))
			throw new ApiRequestException("Informações inválidas");

		List<LocalDate> diasVagos = new ArrayList<LocalDate>();

		final LocalDate diaInicio = LocalDate.of(ano, mes, 1);
		
		final LocalDate diaFim = diaInicio.plusMonths(1);

		for (LocalDate diaLoop = diaInicio; diaLoop.isBefore(diaFim); diaLoop = diaLoop.plusDays(intervaloDias)) {
			horarioLoop: 
				for (LocalTime horarioLoop = horarioInicio; horarioLoop
					.isBefore(horarioFim); horarioLoop = horarioLoop.plusHours(intervaloHoras)) {
				if (agendamentoRepository.findByDiaHorario(diaLoop, horarioLoop) == null) {
					diasVagos.add(diaLoop);
					break horarioLoop;
				}
			}
		}

		return diasVagos;
	}

	public List<LocalTime> listarHorarioVagoDiaPrestador(String dia, String cpfPrestador) {

		DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate diaFormatado = LocalDate.parse(dia, dFormatter);
		
		List<LocalTime> horarioVago = new ArrayList<LocalTime>();

		for (LocalTime horario = horarioInicio; 
				horario.isBefore(horarioFim); 
				horario = horario.plusHours(intervaloHoras)) {
			if (agendamentoRepository.findHorarioByPrestador(cpfPrestador, diaFormatado, horario) == null)
				horarioVago.add(horario);
		}

		if (horarioVago.isEmpty())
			throw new ApiRequestException("Não há horários vagos no dia selecionado");

		return horarioVago;
	}

	public List<Agendamento> procurarPorCpfCliente(String cpfCliente) {
		if (!Utils.validaCpf(cpfCliente))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00");
		
		if (!clienteService.verificaSeClienteExiste(cpfCliente))
			throw new ApiRequestException("Cliente não existe");
		return agendamentoRepository.findByCpfCliente(cpfCliente);
	}

	public List<Agendamento> procurarPorCpfPrestador(String cpfPrestador) {
		if (!Utils.validaCpf(cpfPrestador))
			throw new ApiRequestException(
					"CPF não é válido. Formatos aceitos: 00000000000, 00000000000000, 000.000.000-00, 00.000.000/0000-00, 000000000-00 e 00000000/0000-00");

		if (!prestadorService.verificaSePrestadorExiste(cpfPrestador))
			throw new ApiRequestException("Prestador não encontrado");

		if (agendamentoRepository.findByCpfCliente(cpfPrestador) == null)
			throw new ApiRequestException("Não foram encontrados agendamentos para este cliente");

		return agendamentoRepository.findByCpfPrestador(cpfPrestador);
	}

	public void cancelarAgendamento(Agendamento agendamento) {
		if (verificaHorarioCliente(agendamento))
			throw new ApiRequestException("Agendamento não encontrado");

		agendamentoRepository.deleteByDiaHorarioCliente(agendamento.getCpfCliente(), agendamento.getCpfPrestador(),
				agendamento.getDia(), agendamento.getHorario());
	}

	public Agendamento agendar(Agendamento agendamento) {
		
		// Verificar se o CPF é válido
		
		if(!clienteService.verificaSeClienteExiste(agendamento.getCpfCliente()))
			throw new ApiRequestException("Cliente não existe");
		
		if(!prestadorService.verificaSePrestadorExiste(agendamento.getCpfPrestador()))
			throw new ApiRequestException("Prestador não existe");
		
		if (!verificaHorarioAtual(agendamento))
			throw new ApiRequestException("Não é possível agendar para datas ou horários passados");

		if (!verificaHorarioComercial(agendamento))
			throw new ApiRequestException("Horário inválido. Horários disponíveis entre 8 e 17.");

		if (!verificaDiaUtil(agendamento))
			throw new ApiRequestException("Data inválida. Sábados e domingos não funcionam.");

		if (!verificaDiaCliente(agendamento))
			throw new ApiRequestException("Cliente já possui horário marcado no dia.");

		if (!verificaHorarioCliente(agendamento))
			throw new ApiRequestException("Horário não disponível para o cliente.");

		if (!verificaHorarioPrestador(agendamento))
			throw new ApiRequestException("Horário não disponível para o prestador.");

		return agendamentoRepository.save(agendamento);
	}

	// Validação

	private boolean verificaHorarioComercial(Agendamento agendamento) {
		// Verifica se o horário é comercial
		List<LocalTime> horarios = horarioDeFuncionamento();

		if (!horarios.contains(agendamento.getHorario()))
			return false;

		return true;
	}

	private List<LocalTime> horarioDeFuncionamento() {

		List<LocalTime> horarios = new ArrayList<LocalTime>();

		for (LocalTime horario = horarioInicio; horario
				.isBefore(horarioFim); horario = horario.plusHours(intervaloHoras)) {
			horarios.add(horario);
		}

		return horarios;
	}

	private boolean verificaDiaUtil(Agendamento agendamento) {
		// Verifica se o dia é útil
		if (agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SATURDAY)
				|| agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SUNDAY))
			return false;
		return true;
	}

	private boolean verificaHorarioCliente(Agendamento agendamento) {
		// Verifica se o cliente tem um horário agendado no dia e horário informados
		// Específico para dia e horário
		if (agendamentoRepository.findHorarioByCliente(agendamento.getCpfCliente(), agendamento.getDia(),
				agendamento.getHorario()) != null)
			return false;
		return true;
	}

	private boolean verificaHorarioAtual(Agendamento agendamento) {
		if (agendamento.getDia().isBefore(LocalDate.now()))
			return false;
		if (agendamento.getDia().isEqual(LocalDate.now()) && agendamento.getHorario().isBefore(LocalTime.now()))
			return false;
		return true;
	}

	private boolean verificaDiaCliente(Agendamento agendamento) {
		// Verifica se o cliente possui algum horário no dia informado
		// Específico para apenas o dia
		if (agendamentoRepository.findDiaByCliente(agendamento.getCpfCliente(), agendamento.getDia()) != null)
			return false;
		return true;
	}

	private boolean verificaHorarioPrestador(Agendamento agendamento) {
		// Verifica se o prestador está vago no dia e horário informados
		if (agendamentoRepository.findHorarioByPrestador(agendamento.getCpfPrestador(), agendamento.getDia(),
				agendamento.getHorario()) != null)
			return false;
		return true;
	}

}