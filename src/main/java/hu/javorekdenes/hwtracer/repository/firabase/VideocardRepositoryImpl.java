package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@Repository
public class VideocardRepositoryImpl extends Firebase implements VideocardRepository {
    private static final String COLLECTION_NAME = "videocards";

    @Autowired
    public VideocardRepositoryImpl(FirestoreAdapter firestore) {
        super(firestore);
    }

    @Override
    public List<Videocard> findAllWhereDay(LocalDate date) {
        String dayPrecisePattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dayPrecisePattern).toFormatter();

        List<? extends Hardware> hardwares = firestore
                .getFromCollectionWhereFieldStartsWith(COLLECTION_NAME, "dateString", date.format(formatter));


    }
}
