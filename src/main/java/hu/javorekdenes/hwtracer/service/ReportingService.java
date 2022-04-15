package hu.javorekdenes.hwtracer.service;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.report.DailyReport;

import java.util.List;

public interface ReportingService<T extends ProcessedHardware> {
    DailyReport getDailyReport(List<T> reportSubjects);
}
