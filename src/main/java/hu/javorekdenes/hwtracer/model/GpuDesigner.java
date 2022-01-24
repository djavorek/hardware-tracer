package hu.javorekdenes.hwtracer.model;

import lombok.Getter;

@Getter
public enum GpuDesigner {
    AMD("AMD"),
    NVIDIA("Nvidia"),
    INTEL("Intel");

    private String name;

    GpuDesigner(String name) {
        this.name = name;
    }
}
