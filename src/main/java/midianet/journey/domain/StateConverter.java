package midianet.journey.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StateConverter implements AttributeConverter<Person.State, Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(Person.State value) {
		return value.getValue();
	}
	
	@Override
	public Person.State convertToEntityAttribute(Integer value) {
		return Person.State.toEnum(value);
	}
	
}
