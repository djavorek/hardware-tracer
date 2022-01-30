package hu.javorekdenes.hwtracer.model.raw;

import hu.javorekdenes.hwtracer.model.HardwareType;
import hu.javorekdenes.hwtracer.model.Price;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
public class Hardware {
    private Integer id;

    @NonNull
    private String title;
    @NonNull
    private LocalDate uploadedDate;
    @NonNull
    private Price price;
    @NonNull
    private String url;
    @NonNull
    private HardwareType type;
}
