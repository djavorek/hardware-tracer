package hu.javorekdenes.hwtracer.model;

import lombok.Getter;

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
    GAINWARD("Gainward");

    private String name;

    HardwareManufacturer(String name) {
        this.name = name;
    }
}
