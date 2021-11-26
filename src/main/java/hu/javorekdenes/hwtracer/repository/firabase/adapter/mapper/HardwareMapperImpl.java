package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Objects;

@Component
@Slf4j
public class HardwareMapperImpl implements HardwareMapper {
    // Log template - 1. Invalid field, 2. Invalid value
    private static final String MSG_INVALID_VALUE = "Cannot parse videocard {}, falling back to default. Invalid value: {}";
    // Log template - 1. Null field
    private static final String MSG_NULL_VALUE = "Null received for videocard {}, falling back to default.";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String DATE_FIELD = "dateString";
    private static final String PRICE_FIELD = "price";
    private static final String URL_FIELD = "url";

    public Hardware fromDocumentSnapshot(DocumentSnapshot documentSnapshot) throws MappingException {
        Hardware result = new Hardware();
        try {
            result.setId(parseField(Integer.class, ID_FIELD,  documentSnapshot));
            result.setPrice(parseField(Price.class, PRICE_FIELD, documentSnapshot));
            result.setName(parseField(String.class, NAME_FIELD, documentSnapshot));
            result.setUrl(parseField(String.class, URL_FIELD, documentSnapshot));
            result.setUploadedDate(parseField(LocalDate.class, DATE_FIELD, documentSnapshot));
        } catch (RuntimeException e) {
            throw new MappingException(e);
        }
        return result;
    }

    private <T> T parseField(Class<T> fieldType, String objectName, DocumentSnapshot document) {
        Object fieldValue;

        if (fieldType == Integer.class) {
            fieldValue = parseIntValue(objectName, document.getString(objectName));
        } else if (fieldType == Price.class) {
            fieldValue = parsePriceValue(objectName, document.getString(objectName));
        } else if (fieldType == LocalDate.class) {
            fieldValue = parseDateValue(objectName, document.getString(objectName));
        } else if (fieldType == String.class) {
            System.out.println(document.getString(objectName));
            fieldValue = parseStringValue(objectName, document.getString(objectName));
        } else {
            throw new UnsupportedOperationException("Given field type is not supported");
        }
        return fieldType.cast(fieldValue);
    }

    private Price parsePriceValue(String fieldName, String priceString) throws IllegalArgumentException {
        try {
            String amountString = priceString.substring(0, priceString.lastIndexOf(' ') + 1).replaceAll("\\s", "");
            Integer amount = Integer.parseInt(Objects.requireNonNull(amountString));
            return new Price(amount);
        } catch (NumberFormatException e) {
            log.warn(MSG_INVALID_VALUE, fieldName, priceString);
            throw new IllegalArgumentException(e);
        } catch (NullPointerException npe) {
            log.warn(MSG_NULL_VALUE, fieldName);
            throw new IllegalArgumentException(npe);
        }
    }

    private Integer parseIntValue(String fieldName, String integerString) throws IllegalArgumentException {
        try {
            return Integer.parseInt(Objects.requireNonNull(integerString));
        } catch (NumberFormatException e) {
            log.warn(MSG_INVALID_VALUE, fieldName, integerString);
            throw new IllegalArgumentException(e);
        } catch (NullPointerException npe) {
            log.warn(MSG_NULL_VALUE, fieldName);
            throw new IllegalArgumentException(npe);
        }
    }

    private LocalDate parseDateValue(String fieldName, String dateString) throws IllegalArgumentException {
        if (dateString == null) {
            log.warn(MSG_NULL_VALUE, fieldName);
            throw new IllegalArgumentException();
        }

        try {
            String basePattern = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(basePattern) // .parseLenient()
                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).toFormatter();
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException dateException) {
            log.warn(MSG_INVALID_VALUE, fieldName, dateString);
            throw new IllegalArgumentException(dateException);
        } catch (IllegalArgumentException formatException) {
            String msg = "Invalid date pattern used for mapping videocard fields. Fix it!";
            log.error(msg);
            throw new Error(msg, formatException);
        }
    }

    private String parseStringValue(String fieldName, String string) throws IllegalArgumentException {
        if (string == null) {
            log.warn(MSG_NULL_VALUE, fieldName);
            throw new IllegalArgumentException();
        }

        return string;
    }
}
