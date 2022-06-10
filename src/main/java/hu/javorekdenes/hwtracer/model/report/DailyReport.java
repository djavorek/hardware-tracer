package hu.javorekdenes.hwtracer.model.report;

import hu.javorekdenes.hwtracer.model.Price;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
public class DailyReport {
    private LocalDate date;
    private Integer numberOfItems;
    private Map<ReportedHardware, Price> hardwarePriceMap;
    private Price medianPrice;

    public static class DailyReportBuilder {
        public DailyReportBuilder() {
            this.date = LocalDate.now();
        }
    }
}
