package hu.javorekdenes.hwtracer.service.processing;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;

@FunctionalInterface
public interface HardwareProcessor<S extends Hardware, T extends ProcessedHardware> {
    T process(S toProcess);
}
