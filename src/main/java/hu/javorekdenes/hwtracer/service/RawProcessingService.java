package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
	public RawProcessingService(FirebaseRepository<S> rawRepository, FirebaseRepository<T> processedRepository,
								HardwareProcessor<S, T> hardwareProcessor) {
		this.rawRepository = rawRepository;
		this.processedRepository = processedRepository;
		this.hardwareProcessor = hardwareProcessor;
	}

	public  void processAllUnprocessed() {
		Optional<T> latestProcessed;
		LocalDate lastProcessedDate;
		try {
			latestProcessed = processedRepository.findLatest();
		} catch (RepositoryException e) {
			log.error("Error occurred during reading latest processed videocard, but it exists. Not processing, only serving", e);
			return;
		}

		if (latestProcessed.isEmpty()) {
			lastProcessedDate = LocalDate.of(2021, 1, 1); // Date older than any raw data
		} else {
			lastProcessedDate = latestProcessed.get().getUploadedDate();
		}

		List<S> toProcess = null;

		try {
			toProcess = rawRepository.findAllWhereDay(lastProcessedDate.plus(Duration.ofDays(1)));
		} catch (RepositoryException e) {
			log.error("Cannot process raw videocards as could not load them.", e);
			return;
		}

		List<T> processedVideocards = toProcess.stream()
				.map(hardwareProcessor::process)
				.collect(Collectors.toList());

	}

	private List<S> getRawHardwareWithUploadDate(LocalDate uploadDate) throws HardwareProcessingException {
		try {
			return rawRepository.findAllWhereDay(uploadDate);
		} catch (RepositoryException e) {
			log.error("Cannot process raw videocards as could not load them.", e);
			throw new HardwareProcessingException(e);
		}
	}

	private void processVideocardsWithUploadDate(List<S> toProcess) throws HardwareProcessingException {

	}

}
