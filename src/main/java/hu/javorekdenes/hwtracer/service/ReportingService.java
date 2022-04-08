package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.raw.Hardware;

import java.util.List;

public interface ReportingService<T extends ProcessedHardware> {
    void saveDailyReport(List<T> reportSubjects);
}
