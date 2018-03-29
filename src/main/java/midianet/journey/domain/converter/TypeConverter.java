package midianet.journey.domain.converter;

import midianet.journey.domain.Bedroom;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TypeConverter implements AttributeConverter<Bedroom.Type, String> {
	@Override
	public String convertToDatabaseColumn(Bedroom.Type value) {
		return value.getValue();
	}
	
	@Override
	public Bedroom.Type convertToEntityAttribute(String value) {
		return Bedroom.Type.toEnum(value);
	}

}
