package midianet.journey.domain.converter;

import midianet.journey.domain.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
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