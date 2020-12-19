package jpa.converter;


import jpa.domain.Distance;

import javax.persistence.AttributeConverter;

public class DistanceConverter implements AttributeConverter<Distance, Long> {

    @Override
    public Long convertToDatabaseColumn(final Distance attribute) {
        return attribute.toMeter();
    }

    @Override
    public Distance convertToEntityAttribute(final Long dbData) {
        return Distance.ofMeter(dbData);
    }
}
