package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.DocumentMapper;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;

import java.util.List;
import java.util.Optional;

/**
 * Adapter for official Firestore SDK
 */
public interface FirestoreAdapter<T extends Hardware> {
    // Create
    void saveBatch(String collectionName, List<T> objectsToSave) throws MappingException;

    // Read
    List<T> getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue) throws MappingException;
    List<T> getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith) throws MappingException;
    Optional<T> getLastOrderedByField(String collection, String fieldToOrderBy) throws MappingException;
}
