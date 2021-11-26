package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.VideocardRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ScrapedProcessorService {
	private static final Logger logger = LoggerFactory.getLogger(ScrapedProcessorService.class);
	private VideocardRepository videocardRepository;

	@Autowired
	public ScrapedProcessorService(VideocardRepository videocardRepository) {
		this.videocardRepository = videocardRepository;
	}

	public void testFetch() {
		logger.info("Fetching");
		List<Hardware> results = videocardRepository.findAllWhereDay(LocalDate.of(2021, 10,10));

		results.stream().map(hardware -> hardware.getName()).forEach(log::info);
	}
}
