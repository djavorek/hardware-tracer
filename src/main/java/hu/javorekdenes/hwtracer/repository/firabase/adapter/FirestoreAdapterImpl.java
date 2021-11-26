package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.HardwareMapper;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class FirestoreAdapterImpl implements FirestoreAdapter {
    private Firestore firestore;
    private HardwareMapper hardwareMapper;

    @Autowired
    public FirestoreAdapterImpl(Firestore firestore, HardwareMapper hardwareMapper) {
        this.firestore = firestore;
        this.hardwareMapper = hardwareMapper;
    }

    public List<? extends Hardware> getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue) {
        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection).whereEqualTo(fieldName, fieldValue).get();

        return executeQuery(future);
    }

    /**
     * Searches for items where a string field starts with a given string
     * @param collection
     * @param fieldName Only strings fields
     * @param startsWith
     * @return
     */
    public List<? extends Hardware> getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith) {
        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection)
                .whereGreaterThanOrEqualTo(fieldName, startsWith)
                .whereLessThanOrEqualTo(fieldName, startsWith + "\uf8ff")
                .get();

        return executeQuery(future);
    }

    public Optional<Hardware> getLastOrderedByField(String collection, String fieldToOrderBy) {
        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection)
                .orderBy(fieldToOrderBy)
                .limitToLast(1)
                .get();

        List<? extends Hardware> results = executeQuery(future);

        if (results.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(results.get(0));
        }
    }

    public void saveBatch(String collectionName, List<? extends Hardware> objectsToSave)  {
        CollectionReference collection = this.firestore.collection(collectionName);
        List<ApiFuture<WriteResult>> createFutures = new ArrayList<>();

        objectsToSave.forEach((object) -> {
            DocumentReference document = collection.document();
            createFutures.add(document.create(object));
        });

        createFutures.forEach((future) -> {
            try {
                future.get();
                log.info("New document saved successfully.");
            } catch (InterruptedException e) {
                log.warn("Save ran into interuption");
                throw new FirestoreException(e);
            } catch (ExecutionException e) {
                // log.warn("Execution problem, probably document already exists: " + e.getMessage());
            }
        });

        log.info("Batch firestore save finished.");
    }

    private List<?extends Hardware> executeQuery(ApiFuture<QuerySnapshot> future) {
        List<Hardware> result = new ArrayList<>();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            documents.forEach((document) -> {
                try {
                    result.add(hardwareMapper.fromDocumentSnapshot(document));
                } catch (MappingException e) {
                    log.warn("Document could not be mapped to domain object, it will not be in the results. See: {}", e);
                }
            });
        } catch (InterruptedException e) {
            log.warn("Query ran into interuption");
            throw new FirestoreException(e);
        } catch (ExecutionException e) {
            log.warn("Execution error during query");
            throw new FirestoreException(e);
        }
        return result;
    }
}
