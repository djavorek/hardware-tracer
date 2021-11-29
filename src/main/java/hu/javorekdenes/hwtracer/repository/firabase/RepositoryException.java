package hu.javorekdenes.hwtracer.repository.firabase;

public class RepositoryException extends Exception {
    // Log template. 1: Exact message
    private static final String MSG = "Something went wrong in the repository layer.";

    public RepositoryException() {
        super(MSG);
    }

    public RepositoryException(String message) {
        super(MSG + " See: " + message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(MSG + " See: " + message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(MSG, cause);
    }

    protected RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(MSG + " See: " + message, cause, enableSuppression, writableStackTrace);
    }
}
