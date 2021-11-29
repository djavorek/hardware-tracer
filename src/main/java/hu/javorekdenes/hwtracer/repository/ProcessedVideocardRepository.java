package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface ProcessedVideocardRepository {
    void saveBatch(List<ProcessedVideocard> videocardsToSave) throws RepositoryException;
    Optional<ProcessedVideocard> getLatest() throws RepositoryException;
}
