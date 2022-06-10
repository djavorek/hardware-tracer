package hu.javorekdenes.hwtracer.model.report;

import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.HardwareType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
public class ReportedHardware {
    @NonNull
    private HardwareType type;
    private HardwareManufacturer manufacturer;
    private String modelName;
}
