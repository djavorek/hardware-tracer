package hu.javorekdenes.hwtracer.model;

import java.util.List;

public class Hardwares {
    List<Hardware> hardwares;

    public Hardwares(List<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public List<Hardware> getAll() {
        return hardwares;
    }
}
