package hu.javorekdenes.hwtracer.repository.firabase.adapter;

public class FirestoreException extends RuntimeException {
    private static String MSG = "Something went wrong while communicating with Firestore";

    public FirestoreException() {
        super(MSG);
    }

    public FirestoreException(String message) {
        super(MSG + ": " + message);
    }

    public FirestoreException(String message, Throwable cause) {
        super(MSG + ": " + message, cause);
    }

    public FirestoreException(Throwable cause) {
        super(MSG, cause);
    }

    protected FirestoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(MSG + ": " + message, cause, enableSuppression, writableStackTrace);
    }
}
