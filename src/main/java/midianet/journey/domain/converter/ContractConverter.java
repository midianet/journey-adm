package midianet.journey.domain.converter;

import midianet.journey.domain.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static midianet.journey.domain.Person.Contract.*;

@Converter
public class ContractConverter implements AttributeConverter<Person.Contract, String> {
	@Override
	public String convertToDatabaseColumn(Person.Contract value) {
		return value.getValue();
	}
	
	@Override
	public Person.Contract convertToEntityAttribute(String value) {
		return Person.Contract.toEnum(value);
	}
	

}
