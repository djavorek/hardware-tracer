package hu.javorekdenes.hwtracer.repository.firabase.mapper;

public class MappingException extends Exception {
    private static final String MSG = "Firebase mapping was unsuccessful.";

    public MappingException() {
        super(MSG);
    }

    public MappingException(Throwable cause) {
        super(MSG, cause);
    }
}
