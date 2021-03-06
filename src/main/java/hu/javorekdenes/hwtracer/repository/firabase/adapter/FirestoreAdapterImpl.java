package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.DocumentMapper;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class  FirestoreAdapterImpl<T extends Hardware> implements FirestoreAdapter<T> {
    private final Firestore firestore;

    @Autowired // All implemented mappers
    private List<? extends DocumentMapper<? extends Hardware>> mappers;

    @Autowired
    public FirestoreAdapterImpl(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public List<T> getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue) throws MappingException {
        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection).whereEqualTo(fieldName, fieldValue).get();

        return executeQuery(this.getMapper(collection), future);
    }

    /**
     * Searches for items where a string field starts with a given string
     * @param collection
     * @param fieldName Only strings fields
     * @param startsWith
     * @return
     */
    @Override
    public List<T> getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith) throws MappingException {

        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection)
                .whereGreaterThanOrEqualTo(fieldName, startsWith)
                .whereLessThanOrEqualTo(fieldName, startsWith + "\uf8ff")
                .get();

        return executeQuery(this.getMapper(collection), future);
    }

    @Override
    public Optional<T> getLastOrderedByField(String collectionName, String fieldToOrderBy) throws MappingException {
        DocumentMapper<? extends Hardware> mapper = this.getMapper(collectionName);
        ApiFuture<QuerySnapshot> future = this.firestore.collection(collectionName)
                .orderBy(fieldToOrderBy)
                .limitToLast(1)
                .get();

        List<T> results = executeQuery(this.getMapper(collectionName), future);

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    @Override
    public void saveBatch(String collectionName, List<T> objectsToSave) throws MappingException {
        CollectionReference collection = this.firestore.collection(collectionName);
        List<ApiFuture<WriteResult>> createFutures = new ArrayList<>();
        DocumentMapper<T> mapper = this.getMapper(collectionName);

        objectsToSave.forEach((object) -> {
            DocumentReference document = collection.document();
            try {
                createFutures.add(document.create(mapper.marshall(object)));
            } catch (MappingException e) {
                log.warn("Could not marshall one of the objects during save: ", e);
            }
        });

        createFutures.forEach((future) -> {
            try {
                future.get();
                log.info("New document saved successfully.");
            } catch (InterruptedException e) {
                log.warn("Save ran into interruption");
                throw new FirestoreException(e);
            } catch (ExecutionException e) {
                log.warn("Execution problem, probably document already exists: " + e.getMessage());
            }
        });

        log.info("Batch firestore save finished.");
    }

    @SuppressWarnings("unchecked")
    private DocumentMapper<T> getMapper(String collectionName) throws MappingException {
        return (DocumentMapper<T>) mappers.stream()
                .filter(mapper -> mapper.getCollectionName().equals(collectionName))
                .findFirst()
                .orElseThrow(MappingException::new);
    }

    private List<T> executeQuery(DocumentMapper<T> mapper, ApiFuture<QuerySnapshot> future) throws MappingException {
        List<T> result = new ArrayList<>();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            documents.forEach((document) -> {
                try {
                    result.add(mapper.unmarshall(document));
                } catch (MappingException e) {
                    log.warn("Document could not be mapped to domain object, it will not be in the results. See: {}", e.getCause().getMessage());
                }
            });
            if (documents.size() > result.size()) {
                throw new MappingException();
            }
        } catch (InterruptedException e) {
            log.warn("Query ran into interruption");
            throw new FirestoreException(e);
        } catch (ExecutionException e) {
            log.warn("Execution error during query");
            throw new FirestoreException(e);
        }
        return result;
    }
}
