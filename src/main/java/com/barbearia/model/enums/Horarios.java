package com.barbearia.model.enums;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public enum Horarios {
	OITO(LocalTime.of(8, 0)), NOVE(LocalTime.of(9, 0)), DEZ(LocalTime.of(10, 0)), ONZE(LocalTime.of(11, 0)),
	DOZE(LocalTime.of(12, 0)), TREZE(LocalTime.of(13, 0)), CATORZE(LocalTime.of(14, 0)), QUINZE(LocalTime.of(15, 0)),
	DEZESSEIS(LocalTime.of(16, 0)), DEZESSETE(LocalTime.of(17, 0));

	private LocalTime horario;
	private final static Set<LocalTime> horariosSet = new HashSet<LocalTime>(Horarios.values().length);

	Horarios(LocalTime horario) {
		this.horario = horario;
	}

	static {
		for(Horarios h: Horarios.values()) {
			horariosSet.add(h.getHorario());
		}
	}
	
	public static boolean contains(LocalTime horario) {
		return horariosSet.contains(horario);
	}
	
	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

}
