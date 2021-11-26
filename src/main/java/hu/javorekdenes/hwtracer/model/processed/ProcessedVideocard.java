package hu.javorekdenes.hwtracer.model.processed;

import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import lombok.NonNull;

import java.time.LocalDate;

public class ProcessedVideocard extends ProcessedHardware {
    private int memorySize;

    public ProcessedVideocard(String brand, String modelName, int memorySize) {
        super(brand, modelName);
        this.memorySize = memorySize;
    }

    public ProcessedVideocard(@NonNull String name, @NonNull LocalDate uploadedDate, @NonNull Price price, @NonNull String url, @NonNull HardwareType type, String brand, String modelName, int memorySize) {
        super(name, uploadedDate, price, url, type, brand, modelName);
        this.memorySize = memorySize;
    }
}
