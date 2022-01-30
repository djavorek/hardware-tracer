package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import java.sql.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.GpuDesigner;
import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessedVideocardDocumentMapper extends DocumentMapper<ProcessedVideocard> {
    public static final String COLLECTION_NAME = DocumentCollections.PROCESSED_VIDEOCARD.getCollectionName();

    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String DATE_FIELD = "timestamp";
    public static final String PRICE_FIELD = "price";
    public static final String URL_FIELD = "url";

    public static final String MANUFACTURER_FIELD = "manufacturer";
    public static final String GPU_DESIGNER_FIELD = "gpu_designer";
    public static final String MODEL_FIELD = "model";
    public static final String MEMORY_SIZE_FIELD = "memorySize";
    public static final String WARRANTY_FIELD = "warranty";

    public ProcessedVideocardDocumentMapper() {
        super(COLLECTION_NAME);
    }

    @Override
    public ProcessedVideocard unmarshall(DocumentSnapshot document) throws MappingException {
        ProcessedVideocard result;

        try {
            result = new ProcessedVideocard(
                    parseField(Integer.class, ID_FIELD, document),
                    parseField(String.class, TITLE_FIELD, document),
                    parseField(LocalDate.class, DATE_FIELD, document),
                    parseField(Price.class, PRICE_FIELD, document),
                    parseField(String.class, URL_FIELD, document),
                    parseField(HardwareManufacturer.class, MANUFACTURER_FIELD, document),
                    parseField(GpuDesigner.class, GPU_DESIGNER_FIELD, document),
                    parseField(String.class, MODEL_FIELD, document),
                    parseField(Boolean.class, WARRANTY_FIELD, document),
                    parseField(Integer.class, MEMORY_SIZE_FIELD, document)
            );
        } catch (RuntimeException e) {
            throw new MappingException(e);
        }

        return result;
    }

    @Override
    public Map<String, Object> marshall(ProcessedVideocard object) throws MappingException {
        Map<String, Object> document = new HashMap<>();

        document.put(ID_FIELD, object.getId());
        document.put(TITLE_FIELD, object.getTitle());
        document.put(DATE_FIELD, Timestamp.valueOf(object.getUploadedDate().atStartOfDay()).toString());
        document.put(PRICE_FIELD, object.getPrice().getAmount());
        document.put(URL_FIELD, object.getUrl());

        document.put(MANUFACTURER_FIELD, object.getManufacturer().getName());
        document.put(GPU_DESIGNER_FIELD, object.getGpuDesigner().getName());
        document.put(MODEL_FIELD, object.getModelName());
        document.put(MEMORY_SIZE_FIELD, object.getMemorySize());
        document.put(WARRANTY_FIELD, object.isWarranty());

        return document;
    }
}
