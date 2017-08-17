package net.mehmetatas.devdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends DevDbException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
