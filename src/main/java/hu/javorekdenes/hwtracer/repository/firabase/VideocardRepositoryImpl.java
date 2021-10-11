package hu.javorekdenes.hwtracer.repository.firabase;

import com.google.cloud.firestore.CollectionReference;
import hu.javorekdenes.hwtracer.model.Hardwares;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class VideocardRepositoryImpl extends Firebase implements VideocardRepository {
    private static final String COLLECTION_NAME = "videocards";


    private CollectionReference collectionRef;

    public VideocardRepositoryImpl() {
        this.collectionRef = this.firestore.collection(COLLECTION_NAME);
    }

    @Override
    public Hardwares findAllWhereDate(LocalDate date) {
        return null;
    }
}
