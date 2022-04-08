package hu.javorekdenes.hwtracer.service.reporting;

import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.service.ReportingService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportingServiceImpl<T extends ProcessedHardware> implements ReportingService<T> {

    void saveDailyReport(List<T> reportSubjects) {

    }
}
