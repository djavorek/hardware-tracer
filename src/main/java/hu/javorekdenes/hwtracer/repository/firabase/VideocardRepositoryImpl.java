package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.Hardwares;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Repository
public class VideocardRepositoryImpl extends Firebase implements VideocardRepository {
    private static final String COLLECTION_NAME = "videocards";

    @Autowired
    public VideocardRepositoryImpl(FirestoreAdapter firestore) {
        super(firestore);
    }

    @Override
    public Hardwares findAllWhereDay(LocalDate date) {
        String dayPrecisePattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dayPrecisePattern).toFormatter();

        return firestore.getFromCollectionWhereFieldIs(COLLECTION_NAME, "dateString", date.format(formatter));
    }
}
