package hu.javorekdenes.hwtracer.model.report;

import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ReportedVideocard extends ReportedHardware {
    private HardwareManufacturer manufacturer;
}
