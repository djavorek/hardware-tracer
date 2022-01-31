package hu.javorekdenes.hwtracer.event;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import hu.javorekdenes.hwtracer.service.RawProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class ApplicationEventListener {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

	private final RawProcessingService<Videocard, ProcessedVideocard> videocardProcessingService;

	@Autowired
	public ApplicationEventListener(RawProcessingService<Videocard, ProcessedVideocard> videocardProcessingService) {
		this.videocardProcessingService = videocardProcessingService;
	}

	@EventListener
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("ApplicationReady event received. Kick off processing..");
		videocardProcessingService.processAllUnprocessed();
	}
}