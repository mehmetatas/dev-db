package net.mehmetatas.devdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class Forbidden extends DevDbException {
    public Forbidden(String message) {
        super(message);
    }
}
