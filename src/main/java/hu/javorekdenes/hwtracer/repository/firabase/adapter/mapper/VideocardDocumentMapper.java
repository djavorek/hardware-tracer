package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.raw.Videocard;

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
        return null;
    }

    @Override
    public DocumentSnapshot marshall(Videocard object) throws MappingException {
        return null;
    }
}
