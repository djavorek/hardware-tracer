package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import hu.javorekdenes.hwtracer.model.raw.Hardware;

import java.util.List;

/**
 * Adapter for official Firestore SDK
 */
public interface FirestoreAdapter {
    List<?extends Hardware> getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue);
    List<?extends Hardware> getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith);

    void saveBatch(String collectionName, List<? extends Hardware> objectsToSave);
}
