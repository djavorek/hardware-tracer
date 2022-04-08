package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import hu.javorekdenes.hwtracer.service.processing.ProcessingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ProcessingMediator<S extends Hardware, T extends ProcessedHardware> {
    private final ProcessingServiceImpl<S, T> processingService;


    private final FirebaseRepository<S> rawRepository;
    private final FirebaseRepository<T> processedRepository;

    @Autowired
    public ProcessingMediator(
            ProcessingServiceImpl<S, T> processingService,
            @Qualifier("rawVideocard") FirebaseRepository<S> rawRepository,
            @Qualifier("processedVideocard") FirebaseRepository<T> processedRepository) {
        this.processingService = processingService;
        this.rawRepository = rawRepository;
        this.processedRepository = processedRepository;
    }

    public void processAndReportAllUnprocessed() {
        List<S> unProcessed = collectUnprocessed();
        processingService.process(unProcessed);
    }

    private List<S> collectUnprocessed() {
        Optional<T> latestProcessed;

        try {
            latestProcessed = processedRepository.findLatest();
        } catch (RepositoryException e) {
            log.error("Error occurred during reading latest - existing - processed hardware.", e);
            return Collections.emptyList();
        }

        return getRemainingHardwareToProcess(latestProcessed);
    }

    private List<S> getRemainingHardwareToProcess(Optional<T> latestProcessed) {
        LocalDate lastProcessedDate;
        List<S> toProcess;

        if (latestProcessed.isEmpty()) {
            lastProcessedDate = LocalDate.of(2021, 1, 1); // Date older than any raw data
        } else {
            lastProcessedDate = latestProcessed.get().getUploadedDate();
        }

        try {
            toProcess = rawRepository.findAllWhereDay(lastProcessedDate.plus(1, ChronoUnit.DAYS));
        } catch (RepositoryException e) {
            log.error("Could not load raw hardwares.", e);
            return Collections.emptyList();
        }
        return toProcess;
    }
}
