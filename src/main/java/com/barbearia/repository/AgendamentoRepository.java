package com.barbearia.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barbearia.model.Agendamento;

@Repository
public interface AgendamentoRepository extends CrudRepository<Agendamento, Long>{
	public List<Agendamento> findAll(); 
	public List<Agendamento> findByCpfCliente(String cpf);
	public List<Agendamento> findByCpfPrestador(String cpf);
	public List<Agendamento> findByDia(LocalDate dia);
	public List<Agendamento> findByHorario(LocalDate horario); 
	public void deleteAll();
	
	@Query("SELECT a FROM Agendamento a WHERE a.dia = :dia AND a.horario = :horario AND a.cpfPrestador = :cpfPrestador")
	public Agendamento findHorarioByPrestador(String cpfPrestador, LocalDate dia, LocalTime horario);
	
	@Query("SELECT a FROM Agendamento a WHERE a.dia = :dia and a.horario = :horario and a.cpfCliente = :cpfCliente")
	public Agendamento findHorarioByCliente(String cpfCliente, LocalDate dia, LocalTime horario);
}