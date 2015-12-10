package be.domaindrivendesign.kernel.data.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Java time 8 {@link LocalDateTime} <-> JPA {@link Timestamp} converter
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }
}
