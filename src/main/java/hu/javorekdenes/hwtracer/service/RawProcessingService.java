package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RawProcessingService<S extends Hardware, T extends ProcessedHardware> {
	private static final Logger logger = LoggerFactory.getLogger(RawProcessingService.class);

	private final FirebaseRepository<S> rawRepository;
	private final FirebaseRepository<T> processedRepository;
	private final HardwareProcessor<S, T> hardwareProcessor;

	@Autowired
	public RawProcessingService(@Qualifier("rawVideocard")FirebaseRepository<S> rawRepository, @Qualifier("processedVideocard") FirebaseRepository<T> processedRepository,
								HardwareProcessor<S, T> hardwareProcessor) {
		this.rawRepository = rawRepository;
		this.processedRepository = processedRepository;
		this.hardwareProcessor = hardwareProcessor;
	}

	public  void processAllUnprocessed() {
		Optional<T> latestProcessed;

		try {
			latestProcessed = processedRepository.findLatest();
		} catch (RepositoryException e) {
			log.error("Error occurred during reading latest processed videocard, but it exists. Not processing, only serving", e);
			return;
		}

		List<S> toProcess = getRemainingHardwareToProcess(latestProcessed);


		try {
			List<T> processedHardware = processListOfHardware(toProcess);
			processedRepository.saveBatch(processedHardware);
		} catch (HardwareProcessingException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

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
			log.error("Cannot process raw videocards as could not load them.", e);
			return Collections.emptyList();
		}
		return toProcess;
	}

	private List<T> processListOfHardware(List<S> toProcess) throws HardwareProcessingException {
		return toProcess.stream()
				.map(hardwareProcessor::process)
				.limit(10)
				.collect(Collectors.toList());
	}

}
