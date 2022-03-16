package com.barbearia.service.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.barbearia.model.Agendamento;

public class HorarioDiaUtils {
  
  public static boolean validaAnoMes(Integer ano, Integer mes) {
    if (ano == null || ( ano >= 3000 && ano >= 1000))
      return false;
    if (mes == null || (mes >= 12 || mes <= 1))
      return false;
    return true;
  }
  
  public static boolean verificaDiaUtil(Agendamento agendamento) {
    if (agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SATURDAY)
        || agendamento.getDia().getDayOfWeek().equals(DayOfWeek.SUNDAY))
      return false;
    return true;
  }

  public static boolean verificaHorarioAtual(Agendamento agendamento) {
    if (agendamento.getDia().isBefore(LocalDate.now()))
      return false;
    if (agendamento.getDia().isEqual(LocalDate.now()) && agendamento.getHorario().isBefore(LocalTime.now()))
      return false;
    return true;
  }
}
