package hu.javorekdenes.hwtracer.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import hu.javorekdenes.hwtracer.service.RawProcessingService;

@Component
@Profile("!test")
public class ApplicationEventListener {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

	private RawProcessingService rawProcessingService;

	@Autowired
	public ApplicationEventListener(RawProcessingService rawProcessingService) {
		this.rawProcessingService = rawProcessingService;
	}

	@EventListener
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("ApplicationReady event received");
		rawProcessingService.testFetch();
	}
}