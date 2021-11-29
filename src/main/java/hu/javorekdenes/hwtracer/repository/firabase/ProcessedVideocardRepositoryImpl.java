package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.ProcessedVideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcessedVideocardRepositoryImpl extends Firebase implements ProcessedVideocardRepository {
    private static final String COLLECTION_NAME = "processed_videocards";
    private FirestoreAdapter firestore;

    public ProcessedVideocardRepositoryImpl(FirestoreAdapter firestore) {
        super(firestore);
    }

    @Override
    public void saveBatch(List<ProcessedVideocard> videocardsToSave) {
        List<ProcessedVideocard> sanitizedList = videocardsToSave.stream().filter(Objects::isNull).collect(Collectors.toList());

        try {
            firestore.saveBatch(COLLECTION_NAME, sanitizedList);
        } catch (MappingException e) {
            e.printStackTrace();
        }
    }

    public Optional<ProcessedVideocard> getLatest() throws RepositoryException {
        Optional<Hardware> latestOptional = Optional.empty();
        try {
            latestOptional = firestore.getLastOrderedByField(COLLECTION_NAME, "date");
        } catch (MappingException e) {
            throw new RepositoryException(e);
        }

        if (latestOptional.isEmpty()) {
            return Optional.empty();
        }

        Hardware latest = latestOptional.get();

        if (latest instanceof ProcessedVideocard) {
            return Optional.of((ProcessedVideocard)latest);
        } else {
            return Optional.empty();
        }
    }
}
