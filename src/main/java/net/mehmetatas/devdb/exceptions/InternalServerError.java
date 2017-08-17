package net.mehmetatas.devdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends DevDbException {
    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError(String message, Throwable cuase) {
        super(message, cuase);
    }
}
