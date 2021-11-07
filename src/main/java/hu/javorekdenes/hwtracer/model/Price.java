package hu.javorekdenes.hwtracer.model;

public class Price {
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
