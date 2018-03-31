package midianet.journey.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.sql.Date;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate value) {
		return value != null ? Date.valueOf(value) : null;
	}
	
	@Override
	public LocalDate convertToEntityAttribute(Date value) {
		return value != null ? value.toLocalDate() : null;
	}
	

}
