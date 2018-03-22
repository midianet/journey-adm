package midianet.journey.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Não foi possível remover, existem registros vinculados.")
public class DataDependencyIntegrityException extends BussinesException {

    public DataDependencyIntegrityException() {
        super("Não foi possível remover, existem registros vinculados.");
    }

    public DataDependencyIntegrityException(final Throwable cause) {
        super(cause);
    }

    public static DataDependencyIntegrityException raise() {
        return new DataDependencyIntegrityException();
    }

    public static DataDependencyIntegrityException raise(final Throwable cause) {
        return new DataDependencyIntegrityException(cause);
    }

}