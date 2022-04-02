package hu.javorekdenes.hwtracer.repository.firabase.conrete;

import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.Firebase;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.DocumentCollections;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.VideocardDocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("rawVideocard")
public class VideocardRepositoryImpl extends Firebase<Videocard> implements FirebaseRepository<Videocard> {
    private static final String COLLECTION_NAME = DocumentCollections.VIDEOCARD.getCollectionName();

    @Autowired
    public VideocardRepositoryImpl(FirestoreAdapter<Videocard> firestore) {
        super(firestore);
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    public List<Videocard> findAllWhereDay(LocalDate date) throws RepositoryException {
        String dayPrecisePattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dayPrecisePattern).toFormatter();

        try {
            List<Videocard> hardwares = firestore.getFromCollectionWhereFieldStartsWith(
                    this.getCollectionName(),
                    VideocardDocumentMapper.DATE_FIELD,
                    date.format(formatter));

            return hardwares.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (MappingException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<Videocard> findLatest() throws RepositoryException {
        return getLastOrderedByField(VideocardDocumentMapper.DATE_FIELD);
    }
}
