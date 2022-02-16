package com.barbearia.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Agendamento;
import com.barbearia.model.dto.DiaPrestadorDto;
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

	// Terminar
	public List<LocalDate> listarHorarioVagoMes(String data) {
		if (!validaDataAnoMes(data))
			throw new ApiRequestException("Data inválida. Formato aceito YYYY-MM");

		List<Agendamento> horarioVago = new ArrayList<Agendamento>();

		LocalDate anoMes = formataDataAnoMes(data);

		LocalDate dateLoop = LocalDate.of(anoMes.getYear(), anoMes.getMonthValue(), 1);

		do {

			if (agendamentoRepository.findByDia(dateLoop) != null) {
			}

			dateLoop = dateLoop.plusDays(1);
		} while (dateLoop.getDayOfMonth() < anoMes.lengthOfMonth());

		return null;
	}

	public List<LocalTime> listarHorarioVagoDiaPrestador(DiaPrestadorDto diaPrestadorDto) {
		List<LocalTime> horarioVago = new ArrayList<LocalTime>();
		
		if(diaPrestadorDto.getDia() == null)
			throw new ApiRequestException("Data inválida");
		
		System.out.println(diaPrestadorDto.getDia());

		final LocalTime horarioInicio = LocalTime.of(8, 0);
		final LocalTime horarioFim = LocalTime.of(18, 0);
		final int intervalo = 1;

		for (LocalTime horario = horarioInicio; horario.isBefore(horarioFim); horario = horario.plusHours(intervalo)) {
			if (agendamentoRepository.findHorarioByPrestador(diaPrestadorDto.getCpfPrestador(),
					diaPrestadorDto.getDia(), horario) == null)
				horarioVago.add(horario);
		}

		if (horarioVago.isEmpty())
			throw new ApiRequestException("Não há horários vagos no dia selecionado");

		return horarioVago;
	}

	public List<Agendamento> procurarPorCpfCliente(String cpfCliente) {
		clienteService.validaCpf(cpfCliente);
		clienteService.validaSeClienteNaoExiste(cpfCliente);

		if (agendamentoRepository.findByCpfCliente(cpfCliente) == null)
			throw new ApiRequestException("Não foram encontrados agendamentos para este cliente");

		return agendamentoRepository.findByCpfCliente(cpfCliente);
	}

	public List<Agendamento> procurarPorCpfPrestador(String cpfCliente) {
		prestadorService.validaCpf(cpfCliente);
		prestadorService.validaSePrestadorNaoExiste(cpfCliente);

		if (agendamentoRepository.findByCpfCliente(cpfCliente) == null)
			throw new ApiRequestException("Não foram encontrados agendamentos para este cliente");

		return agendamentoRepository.findByCpfPrestador(cpfCliente);
	}

	public void deletarAgendamento(Agendamento agendamento) {
		if (!verificaHorarioCliente(agendamento))
			throw new ApiRequestException("Agendamento não encontrado");

		agendamentoRepository.deleteByDiaHorarioCliente(agendamento.getCpfCliente(), agendamento.getDia(),
				agendamento.getHorario());
	}

	public void deletarTudo() {
		if (agendamentoRepository.findCount() == 0)
			throw new ApiRequestException("Não há registros a serem exibidos");

		agendamentoRepository.deleteAll();
	}

	public Agendamento agendar(Agendamento agendamento) {
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

	private boolean validaDataAnoMes(String data) {
		String dataRegex = "([2][0-9]{3}\\-0[1-9]|1[0-2])";
		if (data.matches(dataRegex))
			return true;
		return false;
	}

	private boolean verificaHorarioComercial(Agendamento agendamento) {
		final LocalTime horarioInicio = LocalTime.of(8, 0);
		final LocalTime horarioFim = LocalTime.of(18, 0);
		final int hourInterval = 1;
		List<LocalTime> horarios = new ArrayList<LocalTime>();

		for (LocalTime horario = horarioInicio; horario
				.isBefore(horarioFim); horario = horario.plusHours(hourInterval)) {
			horarios.add(horario);
		}

		if (!horarios.contains(agendamento.getHorario()))
			return false;

		return true;
	}

	private boolean verificaDiaUtil(Agendamento agendamento) {
		if (agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SATURDAY)
				|| agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SUNDAY))
			return false;
		return true;
	}

	private boolean verificaHorarioCliente(Agendamento agendamento) {
		if (agendamentoRepository.findHorarioByCliente(agendamento.getCpfCliente(), agendamento.getDia(),
				agendamento.getHorario()) != null)
			return false;
		return true;
	}

	private boolean verificaDiaCliente(Agendamento agendamento) {
		if (agendamentoRepository.findDiaByCliente(agendamento.getCpfCliente(), agendamento.getDia()) != null)
			return false;
		return true;
	}

	private boolean verificaHorarioPrestador(Agendamento agendamento) {
		if (agendamentoRepository.findHorarioByPrestador(agendamento.getCpfPrestador(), agendamento.getDia(),
				agendamento.getHorario()) != null)
			return false;
		return true;
	}

	private LocalDate formataDataAnoMes(String data) {
		// Formata data para YYYY-MM
		return LocalDate.of(Integer.parseInt(data.substring(0, 4)), Integer.parseInt(data.substring(5, 7)), 1);
	}
}