package hu.javorekdenes.hwtracer.repository.firabase;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.FirestoreAdapter;
import hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public abstract class Firebase<T extends Hardware> {
    protected FirestoreAdapter<T> firestore;

    public abstract String getCollectionName();

    @Autowired
    public Firebase(FirestoreAdapter<T> firestore) {
        this.firestore = firestore;
    }

    public void saveBatch(List<T> toSave) {
        List<T> sanitizedList = toSave.stream().filter(Objects::nonNull).collect(Collectors.toList());

        try {
            firestore.saveBatch(getCollectionName(), sanitizedList);
        } catch (MappingException e) {
            e.printStackTrace();
        }
    }

    protected Optional<T> getLastOrderedByField(String fieldToOrderBy) throws RepositoryException {
        Optional<T> latestOptional;
        try {
            latestOptional = firestore.getLastOrderedByField(getCollectionName(), fieldToOrderBy);
        } catch (MappingException e) {
            throw new RepositoryException(e);
        }

        if (latestOptional.isEmpty()) {
            return Optional.empty();
        }

        return latestOptional;
    }
}
