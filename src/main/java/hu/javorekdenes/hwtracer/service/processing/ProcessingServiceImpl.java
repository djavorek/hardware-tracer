package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import hu.javorekdenes.hwtracer.repository.FirebaseRepository;
import hu.javorekdenes.hwtracer.repository.firabase.RepositoryException;
import hu.javorekdenes.hwtracer.service.ProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProcessingServiceImpl<S extends Hardware, T extends ProcessedHardware> implements ProcessingService<S, T> {
	private final FirebaseRepository<T> processedRepository;
	private final HardwareProcessor<S, T> hardwareProcessor;

	public List<T> process(List<S> toProcess) {
		toProcess = toProcess.stream().filter(Objects::nonNull).collect(Collectors.toList());

		if (toProcess.isEmpty()) {
			ProcessingServiceImpl.log.warn("Received empty list of unprocessed hardware. Nothing to do.");
			return Collections.emptyList();
		}

		try {
			List<T> processedHardware = processListOfHardware(toProcess);
			processedRepository.saveBatch(processedHardware);
		} catch (HardwareProcessingException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	@Autowired
	public ProcessingServiceImpl(@Qualifier("processedVideocard") FirebaseRepository<T> processedRepository,
								 HardwareProcessor<S, T> hardwareProcessor) {
		this.processedRepository = processedRepository;
		this.hardwareProcessor = hardwareProcessor;
	}

	private List<T> processListOfHardware(List<S> toProcess) throws HardwareProcessingException {
		return toProcess.stream()
				.map(hardwareProcessor::process)
				.limit(10) // TODO: Testing done
				.collect(Collectors.toList());
	}

}
