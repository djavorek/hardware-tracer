package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import hu.javorekdenes.hwtracer.model.Hardware;
import hu.javorekdenes.hwtracer.model.Hardwares;

import java.util.List;

/**
 * Adapter for official Firestore SDK
 */
public interface FirestoreAdapter {
    Hardwares getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue);
}
