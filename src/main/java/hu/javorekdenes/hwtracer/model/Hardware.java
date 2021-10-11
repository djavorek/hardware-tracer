package hu.javorekdenes.hwtracer.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Hardware {
    private Integer id;

    @NonNull
    private String name;
    @NonNull
    private LocalDate uploadedDate;
    @NonNull
    private Integer price;
    @NonNull
    private String url;
    @NonNull
    private HardwareType type;
}
