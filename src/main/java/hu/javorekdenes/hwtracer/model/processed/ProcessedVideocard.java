package hu.javorekdenes.hwtracer.model.processed;

import hu.javorekdenes.hwtracer.model.GpuDesigner;
import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import lombok.*;

import java.time.LocalDate;

@ToString(callSuper=true)
@Getter
public class ProcessedVideocard extends ProcessedHardware {

    @NonNull
    private final GpuDesigner gpuDesigner;
    private final int memorySize;

    public ProcessedVideocard(String name, LocalDate uploadedDate, Price price, String url,
                              HardwareManufacturer brand, @NonNull GpuDesigner gpuDesigner, String modelName, Boolean warranty, int memorySize) {
        super(name, uploadedDate, price, url, HardwareType.VIDEOCARD, brand, modelName, warranty);
        this.gpuDesigner = gpuDesigner;
        this.memorySize = memorySize;
    }

    public ProcessedVideocard(Videocard videocard, HardwareManufacturer brand, GpuDesigner gpuDesigner, String modelName, Boolean warranty, int memorySize) {
        this(videocard.getName(), videocard.getUploadedDate(), videocard.getPrice(), videocard.getUrl(), brand, gpuDesigner, modelName, warranty, memorySize);
    }

}
