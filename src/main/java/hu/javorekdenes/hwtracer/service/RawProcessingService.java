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
import java.util.Optional;

@Service
@Slf4j
public class RawProcessingService {
	private static final Logger logger = LoggerFactory.getLogger(RawProcessingService.class);
	private final FirebaseRepository<Videocard> videocardRepository;
	private final VideoCardProcessor videocardProcessor;

	@Autowired
	public RawProcessingService(FirebaseRepository<Videocard> videocardRepository, VideoCardProcessor videocardProcessor) {
		this.videocardRepository = videocardRepository;
		this.videocardProcessor = videocardProcessor;
	}

	public void processVideocardBetweenDates(LocalDate from, LocalDate to) {
		// TODO: WIP, under test
		logger.info("Fetching");
		Optional<Videocard> result = Optional.empty();
		try {
			result = videocardRepository.findLatest();

			//results = videocardRepository.findAllWhereDay(LocalDate.of(2021, 10,10));
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		if(result.isPresent()){
			ProcessedVideocard processedVideocard = videocardProcessor.process(result.get());
			System.out.println(processedVideocard);
		}

	}

}
