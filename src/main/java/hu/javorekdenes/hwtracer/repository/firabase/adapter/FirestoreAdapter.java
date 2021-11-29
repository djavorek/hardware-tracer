package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;

import java.util.List;
import java.util.Optional;

/**
 * Adapter for official Firestore SDK
 */
public interface FirestoreAdapter {
    // Create
    void saveBatch(String collectionName, List<? extends Hardware> objectsToSave) throws MappingException;

    // Read
    List<?extends Hardware> getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue) throws MappingException;
    List<?extends Hardware> getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith) throws MappingException;
    Optional<Hardware> getLastOrderedByField(String collection, String fieldToOrderBy) throws MappingException;

}
