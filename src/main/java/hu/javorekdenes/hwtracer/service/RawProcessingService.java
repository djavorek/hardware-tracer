package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import hu.javorekdenes.hwtracer.service.processing.VideocardProcessor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class RawProcessingService {
	private static final Logger logger = LoggerFactory.getLogger(RawProcessingService.class);
	private VideocardRepository videocardRepository;
	private VideocardProcessor videocardProcessor;

	@Autowired
	public RawProcessingService(VideocardRepository videocardRepository, VideocardProcessor videocardProcessor) {
		this.videocardRepository = videocardRepository;
	}

	public void processVideocardBetweenDates(LocalDate from, LocalDate to) {
		// TODO: WIP, under test
		logger.info("Fetching");
		List<Videocard> results = null;
		try {
			results = videocardRepository.findAllWhereDay(LocalDate.of(2021, 10,10));
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		results.stream().map(hardware -> hardware.getName()).forEach(log::info);
	}

}
