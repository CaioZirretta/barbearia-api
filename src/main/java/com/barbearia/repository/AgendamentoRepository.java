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
	
	@Query(value="select a from agendamentos a", nativeQuery = true)
	public Agendamento findHorarioByPrestador(String cpfPrestador, LocalDate dia, LocalTime horario);
	
	@Query(value="select a from agendamentos a", nativeQuery = true)
	public Agendamento findHorarioByCliente(String cpfPrestador, LocalDate dia, LocalTime horario);
}