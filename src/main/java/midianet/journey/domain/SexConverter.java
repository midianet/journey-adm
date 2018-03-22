package midianet.journey.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SexConverter implements AttributeConverter<Person.Sex, String> {
	
	@Override
	public String convertToDatabaseColumn(Person.Sex value) {
		return value.getValue();
	}
	
	@Override
	public Person.Sex convertToEntityAttribute(String value) {
		return Person.Sex.toEnum(value);
	}
	
}