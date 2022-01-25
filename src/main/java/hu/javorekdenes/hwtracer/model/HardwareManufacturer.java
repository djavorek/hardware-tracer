package hu.javorekdenes.hwtracer.model;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

@Getter
public enum HardwareManufacturer {
    ASUS("ASUS"),
    MSI("MSI"),
    GIGABYTE("Gigabyte"),
    EVGA("EVGA"),
    ZOTAC("Zotac"),
    GALAX("Galax"),
    GALAXY("Galaxy"),
    PNY("PNY"),
    PALIT("Palit"),
    POWERCOLOR("Powecolor"),
    SAPPHIRE("Sapphire"),
    INNO3D("Inno3D"),
    XFX("XFX"),
    HIS("HIS"),
    VISIONTEK("VisionTek"),
    ASROCK("ASRock"),
    AFOX("AFOX"),
    MANLI("Manli"),
    BIOSTAR("Biostar"),
    COLORFUL("Colorful"),
    GAINWARD("Gainward"),
    UNKNOWN("Unknown brand or Reference design");

    private final String name;

    HardwareManufacturer(String name) {
        this.name = name;
    }

    public static HardwareManufacturer fromName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(HardwareManufacturer.UNKNOWN);
    }
}
