package hu.javorekdenes.hwtracer.repository.firabase.adapter.mapper;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum DocumentCollections {
    VIDEOCARD("videocards"),
    PROCESSED_VIDEOCARD("processed_videocards");

    private String collectionName;

    DocumentCollections(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public static Optional<DocumentCollections> of(String collectionName) {
        return Arrays.stream(values()).filter(v -> Objects.equals(collectionName, v.getCollectionName())).findFirst();
    }
}
