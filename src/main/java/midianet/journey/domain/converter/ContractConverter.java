package midianet.journey.domain.converter;

import midianet.journey.domain.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static midianet.journey.domain.Person.Contract.*;

@Converter
public class ContractConverter implements AttributeConverter<Person.Contract, String> {
	@Override
	public String convertToDatabaseColumn(Person.Contract value) {
		switch (value) {
			case AGREE:
				return "A";
			case DISAGRE:
				return "D";
			default:
				return "";
		}
	}
	
	@Override
	public Person.Contract convertToEntityAttribute(String value) {
		switch (value) {
			case "A":
				return AGREE;
			case "D":
				return DISAGRE;
			default:
				return BLANK;
		}
	}
	

}
