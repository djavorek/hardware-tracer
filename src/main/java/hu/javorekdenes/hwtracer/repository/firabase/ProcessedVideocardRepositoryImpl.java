package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.repository.ProcessedVideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProcessedVideocardRepositoryImpl extends Firebase implements ProcessedVideocardRepository {
    private static final String COLLECTION_NAME = "processed_videocards";

    public ProcessedVideocardRepositoryImpl(FirestoreAdapter firestore) {
        super(firestore);
    }

    @Override
    public void saveBatch(List<ProcessedVideocard> videocardsToSave) {
        List<ProcessedVideocard> sanitizedList = videocardsToSave.stream().filter(Objects::isNull).collect(Collectors.toList());

        firestore.saveBatch(COLLECTION_NAME, sanitizedList);
    }

    public ProcessedVideocard getLatest() {
        firestore.get
    }
}
