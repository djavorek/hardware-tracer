package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RawProcessingService {
	private static final Logger logger = LoggerFactory.getLogger(RawProcessingService.class);
	private final FirebaseRepository<Videocard> videocardRepository;
	private final FirebaseRepository<ProcessedVideocard> processedVideocardRepository;
	private final VideoCardProcessor videocardProcessor;

	@Autowired
	public RawProcessingService(FirebaseRepository<Videocard> videocardRepository, FirebaseRepository<ProcessedVideocard> processedVideocardRepository,
								VideoCardProcessor videocardProcessor) {
		this.videocardRepository = videocardRepository;
		this.processedVideocardRepository = processedVideocardRepository;
		this.videocardProcessor = videocardProcessor;
	}

	public void processVideocardBetweenDates(LocalDate from, LocalDate to) {
		// TODO: WIP, under test
		logger.info("Fetching");
		Optional<Videocard> result = Optional.empty();
		try {
			result = videocardRepository.findLatest();

			//results = videocardRepository.findAllWhereDay(LocalDate.of(2022, 1,1));
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		if (result.isPresent()){
			ProcessedVideocard processedVideocard = videocardProcessor.process(result.get());
			System.out.println(processedVideocard);
			try {
				processedVideocardRepository.saveBatch(List.of(processedVideocard));
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}

		try {
			// TODO: Continue
			Optional<ProcessedVideocard> processedVideocardBack = processedVideocardRepository.findLatest();
			System.out.println(processedVideocardBack.get());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}


	}

}
