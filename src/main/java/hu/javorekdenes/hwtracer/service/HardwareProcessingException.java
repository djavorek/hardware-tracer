package hu.javorekdenes.hwtracer.service;

public class HardwareProcessingException extends Exception {
    public static final String MSG = "Error during processing raw hardware items. ";

    public HardwareProcessingException() {
        super(MSG);
    }

    public HardwareProcessingException(String message) {
        super(MSG + message);
    }

    public HardwareProcessingException(String message, Throwable cause) {
        super(MSG + message, cause);
    }

    public HardwareProcessingException(Throwable cause) {
        super(MSG, cause);
    }
}
