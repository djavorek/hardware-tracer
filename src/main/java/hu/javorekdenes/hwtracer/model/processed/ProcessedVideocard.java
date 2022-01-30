package hu.javorekdenes.hwtracer.model.processed;

import hu.javorekdenes.hwtracer.model.GpuDesigner;
import hu.javorekdenes.hwtracer.model.HardwareManufacturer;
import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import hu.javorekdenes.hwtracer.model.raw.Videocard;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@ToString(callSuper=true)
@Getter
public class ProcessedVideocard extends ProcessedHardware implements Serializable {
    private static final long serialVersionUID = 54083223601L;

    private final GpuDesigner gpuDesigner;
    private final int memorySize;

    public ProcessedVideocard(Integer id, String title, LocalDate uploadedDate, Price price, String url,
                              HardwareManufacturer brand, GpuDesigner gpuDesigner, String modelName, Boolean warranty, int memorySize) {
        super(id, title, uploadedDate, price, url, HardwareType.VIDEOCARD, brand, modelName, warranty);
        this.gpuDesigner = gpuDesigner;
        this.memorySize = memorySize;
    }

    public ProcessedVideocard(String title, LocalDate uploadedDate, Price price, String url,
                              HardwareManufacturer brand, GpuDesigner gpuDesigner, String modelName, Boolean warranty, int memorySize) {
        super(title, uploadedDate, price, url, HardwareType.VIDEOCARD, brand, modelName, warranty);
        this.gpuDesigner = gpuDesigner;
        this.memorySize = memorySize;
    }

    public ProcessedVideocard(Videocard videocard, HardwareManufacturer brand, GpuDesigner gpuDesigner, String modelName, Boolean warranty, int memorySize) {
        this(videocard.getId(), videocard.getTitle(), videocard.getUploadedDate(), videocard.getPrice(), videocard.getUrl(),
                brand, gpuDesigner, modelName, warranty, memorySize);
    }

}
