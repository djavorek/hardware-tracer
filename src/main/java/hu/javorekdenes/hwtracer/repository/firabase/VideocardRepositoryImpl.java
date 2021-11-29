package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.VideocardDocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VideocardRepositoryImpl extends Firebase implements VideocardRepository {

    @Autowired
    public VideocardRepositoryImpl(FirestoreAdapter firestore) {
        super(firestore);
    }

    @Override
    public List<Videocard> findAllWhereDay(LocalDate date) throws RepositoryException {
        String dayPrecisePattern = "yyyy-MM-dd";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern(dayPrecisePattern).toFormatter();

        try {
            List<? extends Hardware> hardwares = firestore
                    .getFromCollectionWhereFieldStartsWith(VideocardDocumentMapper.COLLECTION_NAME,
                            VideocardDocumentMapper.DATE_FIELD, date.format(formatter));

            return (List<Videocard>) hardwares.stream().filter(hardware -> hardware instanceof Videocard).collect(Collectors.toList());
        } catch (MappingException e) {
            throw new RepositoryException(e);
        }
    }
}
