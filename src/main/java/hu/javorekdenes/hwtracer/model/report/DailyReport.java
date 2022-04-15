package hu.javorekdenes.hwtracer.model.report;

import hu.javorekdenes.hwtracer.model.Price;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DailyReport {
    private LocalDate date;
    private Integer numberOfItems;
    private List<String> mostPopularTags;
    private Price medianPrice;

    public static class DailyReportBuilder {
        public DailyReportBuilder() {
            this.date = LocalDate.now();
            this.mostPopularTags = new ArrayList<>();
        }
    }
}
