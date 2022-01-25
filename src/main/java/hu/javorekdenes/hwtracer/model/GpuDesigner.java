package hu.javorekdenes.hwtracer.model;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

@Getter
public enum GpuDesigner {
    AMD("AMD"),
    NVIDIA("Nvidia"),
    INTEL("Intel"),
    UNKNOWN("Unknown brand");

    private final String name;

    GpuDesigner(String name) {
        this.name = name;
    }

    @Nullable
    public static GpuDesigner fromName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
