package hu.javorekdenes.hwtracer.repository.firabase.adapter;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import hu.javorekdenes.hwtracer.model.Hardware;
import hu.javorekdenes.hwtracer.model.Hardwares;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.HardwareMapper;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    public Hardwares getFromCollectionWhereFieldIs(String collection, String fieldName, String fieldValue) {
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
    public Hardwares getFromCollectionWhereFieldStartsWith(String collection, String fieldName, String startsWith) {
        List<Hardware> result = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = this.firestore.collection(collection)
                .whereGreaterThanOrEqualTo(fieldName, startsWith)
                .whereLessThanOrEqualTo(fieldName, startsWith + "\uf8ff")
                .get();

        return executeQuery(future);
    }

    private Hardwares executeQuery(ApiFuture<QuerySnapshot> future) {
        List<Hardware> result = new ArrayList<>();

        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (DocumentSnapshot document : documents) {
                try {
                    result.add(hardwareMapper.fromDocumentSnapshot(document));
                } catch (MappingException e) {
                    log.warn("Document could not be mapped to domain object, it will not be in the results. See: {}", e);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new Hardwares(result);
    }
}
