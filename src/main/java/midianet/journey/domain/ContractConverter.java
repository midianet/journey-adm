package midianet.journey.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
