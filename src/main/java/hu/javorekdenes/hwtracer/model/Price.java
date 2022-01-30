package hu.javorekdenes.hwtracer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public final class Price {
    private static final String FORINT = "Ft";

    private Integer amount;
    private String currency;

    public Price(Integer amount) {
        this.amount = amount;
        this.currency = FORINT;
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
