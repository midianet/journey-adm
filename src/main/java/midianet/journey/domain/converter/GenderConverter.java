package midianet.journey.domain.converter;

import midianet.journey.domain.Bedroom;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Bedroom.Gender, String> {
	@Override
	public String convertToDatabaseColumn(Bedroom.Gender value) {
		return value.getValue();
	}
	
	@Override
	public Bedroom.Gender convertToEntityAttribute(String value) {
		return Bedroom.Gender.toEnum(value);
	}
	

}
