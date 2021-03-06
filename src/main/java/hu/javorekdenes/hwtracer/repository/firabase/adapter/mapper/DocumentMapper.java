package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class DocumentMapper<T extends Hardware> {
    // Log template - 1. Invalid field, 2. Invalid value
    private static final String MSG_INVALID_VALUE = "Cannot parse videocard {}, falling back to default. Invalid value: {}";
    // Log template - 1. Null field
    private static final String MSG_NULL_VALUE = "Null received for videocard {}, falling back to default.";

    protected final String collectionName;
    protected Firestore firestore;

    public DocumentMapper(String collectionName) {
        this.collectionName = collectionName;
    }

    public abstract T unmarshall(DocumentSnapshot document) throws MappingException;
    public abstract Map<String,Object> marshall(T object) throws MappingException;

    public String getCollectionName() {
        return collectionName;
    }

    @Autowired
    public void setFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    <Z> Z parseField(Class<Z> fieldType, String objectName, DocumentSnapshot document) throws RuntimeException {
        Object fieldValue;

        if (fieldType == Integer.class) {
            fieldValue = parseIntValue(objectName, document.get(objectName));
        } else if (fieldType == Price.class) {
            fieldValue = parsePriceValue(objectName, document.get(objectName));
        } else if (fieldType == LocalDate.class) {
            fieldValue = parseDateValue(objectName, document.getString(objectName));
        } else if (fieldType == String.class) {
            fieldValue = parseStringValue(objectName, document.getString(objectName));
        } else if (fieldType == Boolean.class) {
            fieldValue = parseBooleanValue(objectName, document.getBoolean(objectName));
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            fieldValue = parseEnumValue(fieldType, objectName, document.getString(objectName));
        } else {
            throw new UnsupportedOperationException("Given field type is not supported");
        }
        return fieldType.cast(fieldValue);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Enum parseEnumValue(Class enumClass, String fieldName,  String enumNameValue) {
        try {
            return Enum.valueOf(enumClass, enumNameValue);
        } catch (IllegalArgumentException e) {
            log.warn(MSG_INVALID_VALUE, fieldName, enumNameValue);
            throw new IllegalArgumentException(e);
        }
    }

    Price parsePriceValue(String fieldName, Object priceObj) throws IllegalArgumentException {
        if (priceObj instanceof Long) {
            return new Price(((Long) priceObj).intValue());
        } else if (priceObj instanceof String) {
            String priceString = (String) priceObj;
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
        throw new IllegalArgumentException("Not supported type to parse Price from.");
    }

    private Integer parseIntValue(String fieldName, Object integerObj) throws IllegalArgumentException {
        if (integerObj instanceof Integer) {
            return (Integer) integerObj;
        } else if (integerObj instanceof Long) {
            return ((Long) integerObj).intValue();
        } else if (integerObj instanceof String) {
            try {
                return Integer.parseInt(Objects.requireNonNull((String) integerObj));
            } catch (NumberFormatException e) {
                log.warn(MSG_INVALID_VALUE, fieldName, integerObj);
                throw new IllegalArgumentException(e);
            } catch (NullPointerException npe) {
                log.warn(MSG_NULL_VALUE, fieldName);
                throw new IllegalArgumentException(npe);
            }
        }
        throw new IllegalArgumentException("Not supported type to parse integer from.");
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

    private Boolean parseBooleanValue(String fieldName, Boolean booleanValue) {
        if (booleanValue == null) {
            log.warn(MSG_NULL_VALUE, fieldName);
            throw new IllegalArgumentException();
        }

        return booleanValue;
    }
}
