package com.barbearia.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barbearia.model.Agendamento;

@Repository
public interface AgendamentoRepository extends CrudRepository<Agendamento, Long>{
	public List<Agendamento> findAll();
	
	// Read
	
	@Query("SELECT count(a) FROM Agendamento a")
	public Integer findCount();
	
	public List<Agendamento> findByCpfCliente(String cpf);
	public List<Agendamento> findByCpfPrestador(String cpf);
	public List<Agendamento> findByDia(LocalDate dia);
	public List<Agendamento> findByHorario(LocalDate horario); 
	
	@Query("SELECT a FROM Agendamento a WHERE a.dia = :dia AND a.horario = :horario ORDER BY a.dia")
	public Agendamento findByDiaHorario(LocalDate dia, LocalTime horario);
	
	@Query("SELECT a FROM Agendamento a WHERE a.cpfCliente = :cpfCliente AND a.dia = :dia ORDER BY a.dia")
	public Agendamento findDiaByCliente(String cpfCliente, LocalDate dia);
	
	@Query("SELECT a FROM Agendamento a WHERE a.cpfCliente = :cpfCliente AND a.dia = :dia AND a.horario = :horario ORDER BY a.dia")
	public Agendamento findHorarioByCliente(String cpfCliente, LocalDate dia, LocalTime horario);
	
	@Query("SELECT a FROM Agendamento a WHERE a.cpfPrestador = :cpfPrestador AND a.dia = :dia AND a.horario = :horario ORDER BY a.dia")
	public Agendamento findHorarioByPrestador(String cpfPrestador, LocalDate dia, LocalTime horario);

	@Query("SELECT a FROM Agendamento a WHERE a.cpfCliente = :cpfCliente AND a.cpfPrestador = :cpfPrestador AND a.dia = :dia AND a.horario = :horario ORDER BY a.dia")
	public Agendamento findByClientePrestadorDiaHorario(String cpfCliente, String cpfPrestador, LocalDate dia, LocalTime horario);
	
	
	// Delete
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Agendamento a WHERE a.cpfCliente = :cpfCliente AND a.cpfPrestador = :cpfPrestador AND a.dia = :dia AND a.horario = :horario")
	public void deleteByDiaHorarioCliente(String cpfCliente, String cpfPrestador, LocalDate dia, LocalTime horario);
	
}