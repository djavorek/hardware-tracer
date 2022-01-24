package hu.javorekdenes.hwtracer.model.raw;

import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import lombok.NonNull;

import java.time.LocalDate;

public class Videocard extends Hardware {
    public Videocard(@NonNull Integer id, @NonNull String name, @NonNull LocalDate uploadedDate, @NonNull Price price, @NonNull String url) {
        super(id, name, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }

    public Videocard(@NonNull String name, @NonNull LocalDate uploadedDate, @NonNull Price price, @NonNull String url) {
        super(name, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }
}
