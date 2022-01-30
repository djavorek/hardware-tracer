package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class VideocardDocumentMapper extends DocumentMapper<Videocard> {
    public static final String COLLECTION_NAME = DocumentCollections.VIDEOCARD.getCollectionName();

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DATE_FIELD = "dateString";
    public static final String PRICE_FIELD = "price";
    public static final String URL_FIELD = "url";

    public VideocardDocumentMapper() {
        super(COLLECTION_NAME);
    }

    @Override
    public Videocard unmarshall(DocumentSnapshot document) throws MappingException {
        Videocard result;

        try {
            result = new Videocard(
                    parseField(Integer.class, ID_FIELD, document),
                    parseField(String.class, NAME_FIELD, document),
                    parseField(LocalDate.class, DATE_FIELD, document),
                    parseField(Price.class, PRICE_FIELD, document),
                    parseField(String.class, URL_FIELD, document)
            );
        } catch (RuntimeException e) {
            throw new MappingException(e);
        }

        return result;
    }

    @Override
    public Map<String, Object> marshall(Videocard object) throws MappingException {
        // TODO
        throw new NotImplementedException();
    }
}
