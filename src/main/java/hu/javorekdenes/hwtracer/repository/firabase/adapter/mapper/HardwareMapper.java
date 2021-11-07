package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import com.google.cloud.firestore.DocumentSnapshot;
import hu.javorekdenes.hwtracer.model.Hardware;

public interface HardwareMapper {
    Hardware fromDocumentSnapshot(DocumentSnapshot documentSnapshot) throws MappingException;
}
