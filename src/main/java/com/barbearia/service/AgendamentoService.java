package com.barbearia.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbearia.enums.MensagensAgendamento;
import com.barbearia.enums.MensagensPessoas;
import com.barbearia.exception.ApiRequestException;
import com.barbearia.model.Agendamento;
import com.barbearia.repository.AgendamentoRepository;
import com.barbearia.service.utils.CpfUtils;

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
			throw new ApiRequestException(MensagensAgendamento.TABELA_AGENDAMENTO_VAZIA.getMensagem());
		return agendamentoRepository.findAll();
	}

	public List<LocalDate> listarHorarioVagoMes(Integer ano, Integer mes) {

		if (!CpfUtils.validaAnoMes(ano, mes))
			throw new ApiRequestException(MensagensAgendamento.ANO_MES_INVALIDOS.getMensagem());

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
			throw new ApiRequestException(MensagensAgendamento.HORARIO_OCUPADO.getMensagem());

		return horarioVago;
	}

	public List<Agendamento> procurarPorCpfCliente(String cpfCliente) {
		if (!CpfUtils.validaCpf(cpfCliente))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());
		
		if (!clienteService.verificaSeClienteExiste(cpfCliente))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());
		return agendamentoRepository.findByCpfCliente(cpfCliente);
	}

	public List<Agendamento> procurarPorCpfPrestador(String cpfPrestador) {
		if (!CpfUtils.validaCpf(cpfPrestador))
			throw new ApiRequestException(MensagensPessoas.CPF_INVALIDO.getMensagem());

		if (!prestadorService.verificaSePrestadorExiste(cpfPrestador))
			throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());

		if (agendamentoRepository.findByCpfCliente(cpfPrestador) == null)
			throw new ApiRequestException(MensagensAgendamento.CLIENTE_SEM_AGENDAMENTO.getMensagem());

		return agendamentoRepository.findByCpfPrestador(cpfPrestador);
	}

	public void cancelarAgendamento(Agendamento agendamento) {
		if (verificaHorarioCliente(agendamento))
			throw new ApiRequestException(MensagensAgendamento.AGENDAMENTO_NAO_ENCONTRADO.getMensagem());

		agendamentoRepository.deleteByDiaHorarioCliente(agendamento.getCpfCliente(), agendamento.getCpfPrestador(),
				agendamento.getDia(), agendamento.getHorario());
	}

	public Agendamento agendar(Agendamento agendamento) {
		
		// Verificar se o CPF é válido
		
		if(!clienteService.verificaSeClienteExiste(agendamento.getCpfCliente()))
			throw new ApiRequestException(MensagensPessoas.CLIENTE_NAO_EXISTE.getMensagem());
		
		if(!prestadorService.verificaSePrestadorExiste(agendamento.getCpfPrestador()))
			throw new ApiRequestException(MensagensPessoas.PRESTADOR_NAO_EXISTE.getMensagem());
		
		if (!verificaHorarioAtual(agendamento))
			throw new ApiRequestException(MensagensAgendamento.HORARIO_FUTURO_INVALIDO.getMensagem());

		if (!verificaHorarioComercial(agendamento))
			throw new ApiRequestException(MensagensAgendamento.HORARIO_NAO_COMERCIAL.getMensagem());

		if (!verificaDiaUtil(agendamento))
			throw new ApiRequestException(MensagensAgendamento.DATA_NAO_UTIL.getMensagem());

		if (!verificaDiaCliente(agendamento))
			throw new ApiRequestException(MensagensAgendamento.CLIENTE_HORARIO_OCUPADO_DIA.getMensagem());

		if (!verificaHorarioCliente(agendamento))
			throw new ApiRequestException(MensagensAgendamento.CLIENTE_HORARIO_OCUPADO.getMensagem());

		if (!verificaHorarioPrestador(agendamento))
			throw new ApiRequestException(MensagensAgendamento.PRESTADOR_HORARIO_OCUPADO.getMensagem());

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