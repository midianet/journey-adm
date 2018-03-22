package midianet.journey.exception;


//TODO: Implement Internationalization
public abstract class BussinesException extends RuntimeException {

    public BussinesException() {
        super();
    }

    public BussinesException(final String message) {
        super(message);
    }

    public BussinesException(final String message,final Throwable cause) {
        super(message, cause);
    }

    public BussinesException(final Throwable cause) {
        super(cause);
    }

    protected BussinesException(final String message, final Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
