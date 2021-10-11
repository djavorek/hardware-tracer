package hu.javorekdenes.hwtracer.model;

import lombok.NonNull;

import java.time.LocalDate;

public class Videocard extends Hardware {
    public Videocard(Integer id, String name, LocalDate uploadedDate, Integer price, String url) {
        super(id, name, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }

    public Videocard(@NonNull String name, @NonNull LocalDate uploadedDate, @NonNull Integer price, @NonNull String url) {
        super(name, uploadedDate, price, url, HardwareType.VIDEOCARD);
    }
}
