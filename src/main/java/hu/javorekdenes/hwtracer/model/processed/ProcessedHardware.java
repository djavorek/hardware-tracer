package hu.javorekdenes.hwtracer.model.processed;

import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Hardware;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString(callSuper=true)
public class ProcessedHardware extends Hardware {
    private HardwareManufacturer manufacturer;
    private String modelName;
    private boolean warranty;

    public ProcessedHardware(Integer id, String title, LocalDate uploadedDate, Price price, String url, HardwareType type, HardwareManufacturer manufacturer, String modelName, Boolean warranty) {
        super(id, title, uploadedDate, price, url, type);
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.warranty = warranty;
    }

    public ProcessedHardware(String name, LocalDate uploadedDate, Price price, String url, HardwareType type, HardwareManufacturer manufacturer, String modelName, Boolean warranty) {
        super(name, uploadedDate, price, url, type);
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.warranty = warranty;
    }
}
