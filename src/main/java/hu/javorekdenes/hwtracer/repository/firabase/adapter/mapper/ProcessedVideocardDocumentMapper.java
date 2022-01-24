package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

@Component
public class ProcessedVideocardDocumentMapper extends DocumentMapper<ProcessedVideocard> {
    public static final String COLLECTION_NAME = DocumentCollections.PROCESSED_VIDEOCARD.getCollectionName();

    public static final String ID_FIELD = "id";
    public static final String BRAND_FIELD = "brand";
    public static final String MODEL_FIELD = "model";
    public static final String MEMORY_TYPE_FIELD = "memoryType";
    public static final String MEMORY_SIZE_FIELD = "memorySize";

    public static final String DATE_FIELD = "timestamp";
    public static final String PRICE_FIELD = "price";
    public static final String URL_FIELD = "url";

    public ProcessedVideocardDocumentMapper() {
        super(COLLECTION_NAME);
    }

    @Override
    public ProcessedVideocard unmarshall(DocumentSnapshot document) throws MappingException {
        throw new NotImplementedException();
    }
}
