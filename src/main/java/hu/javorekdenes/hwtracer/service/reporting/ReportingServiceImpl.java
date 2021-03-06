package hu.javorekdenes.hwtracer.service.reporting;

import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.processed.ProcessedHardware;
import hu.javorekdenes.hwtracer.model.report.DailyReport;
import hu.javorekdenes.hwtracer.model.report.ReportedHardware;
import hu.javorekdenes.hwtracer.service.ReportingService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReportingServiceImpl<T extends ProcessedHardware> implements ReportingService<T> {

    public DailyReport getDailyReport(List<T> reportSubjects) {
        DailyReport.DailyReportBuilder reportBuilder = DailyReport.builder();

        List<Price> subjectPrices = new ArrayList<>();
        Map<ReportedHardware, Price> hardwarePriceMap = new HashMap<>();
        // TODO: Fill hardwarePriceMap

        for (T reportSubject : reportSubjects) {
            subjectPrices.add(reportSubject.getPrice());
        }

        Collections.sort(subjectPrices);

        Price medianPrice = subjectPrices.get(subjectPrices.size() / 2);
        if (subjectPrices.size() % 2 == 0) {
            medianPrice = new Price(medianPrice.getAmount() + subjectPrices.get(subjectPrices.size() / 2 - 1).getAmount() / 2);
        }

        reportBuilder.hardwarePriceMap(hardwarePriceMap);
        reportBuilder.numberOfItems(reportSubjects.size());
        reportBuilder.medianPrice(medianPrice);

        return reportBuilder.build();
    }
}
