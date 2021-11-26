package hu.javorekdenes.hwtracer.model.processed;

import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ProcessedHardware extends Hardware {
    @NonNull private String brand;
    @NonNull private String modelName;
    private boolean warranty;

    public ProcessedHardware(String name, LocalDate uploadedDate, Price price, String url, HardwareType type, String brand, String modelName) {
        super(name, uploadedDate, price, url, type);
        this.brand = brand;
        this.modelName = modelName;
    }
}
