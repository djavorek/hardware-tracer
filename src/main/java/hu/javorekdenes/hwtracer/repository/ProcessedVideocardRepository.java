package hu.javorekdenes.hwtracer.repository;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;

import java.util.List;

public interface ProcessedVideocardRepository {
    void saveBatch(List<ProcessedVideocard> videocardsToSave);
}
