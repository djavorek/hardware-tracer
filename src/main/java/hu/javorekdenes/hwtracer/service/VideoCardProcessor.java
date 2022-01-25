package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedVideocard;
import hu.javorekdenes.hwtracer.model.raw.Videocard;

public interface VideoCardProcessor {
    ProcessedVideocard process(Videocard toProcess);
}
