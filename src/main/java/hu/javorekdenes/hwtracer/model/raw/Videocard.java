package hu.javorekdenes.hwtracer.model.raw;

import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import lombok.*;

import java.time.LocalDate;

@ToString(callSuper=true)
public class Videocard extends Hardware {
    public Videocard(@NonNull Integer id, @NonNull String title, @NonNull LocalDate uploadedDate, @NonNull Price price, @NonNull String url) {
        super(id, title, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }

    public Videocard(@NonNull String title, @NonNull LocalDate uploadedDate, @NonNull Price price, @NonNull String url) {
        super(title, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }


}
