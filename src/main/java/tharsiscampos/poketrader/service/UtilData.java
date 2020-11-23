package tharsiscampos.poketrader.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UtilData {

	public static ZoneId ZONE_ID_AMERICA_SAO_PAULO = ZoneId.of("America/Sao_Paulo");

	public static DateTimeFormatter FORMATADOR_USUARIO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	public static String toUsuarioDataHora(Date d) {
		
		if (d == null) return "";
		
		LocalDateTime ldt = dateToLocalDateTime(d, ZONE_ID_AMERICA_SAO_PAULO);
		return FORMATADOR_USUARIO_DATA_HORA.format(ldt);
	}

	public static LocalDateTime dateToLocalDateTime(Date d, ZoneId zId) {
		if (d == null) return null;
		return LocalDateTime.ofInstant(d.toInstant(), zId);
	}

	public static LocalDate dateToLocalDate(Date d, ZoneId zId) {
		
		if (d == null) return null;
		
		if (d instanceof java.sql.Date) {
			return ((java.sql.Date) d).toLocalDate();
		} else {
			return d.toInstant().atZone(zId).toLocalDate();
		}
	}
}
