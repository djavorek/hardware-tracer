package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import org.springframework.stereotype.Component;

@Component
public class VideocardProcessor {

    public ProcessedVideocard processVideocard(Videocard toProcess) {
        String title = toProcess.getName();

    }

    private HardwareManufacturer findManufacturer(String title) {

    }
}
