package midianet.journey.domain.converter;

import midianet.journey.domain.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StateConverter implements AttributeConverter<Person.State, String> {

	@Override
	public String convertToDatabaseColumn(Person.State value) {
		return value.getValue();
	}
	
	@Override
	public Person.State convertToEntityAttribute(String value) {
		return Person.State.toEnum(value);
	}
	
}
