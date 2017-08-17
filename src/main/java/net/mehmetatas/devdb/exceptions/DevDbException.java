package net.mehmetatas.devdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class DevDbException extends RuntimeException {
    protected DevDbException(String message) {
        super(message);
    }

    protected DevDbException(String message, Throwable cuase) {
        super(message, cuase);
    }

    private static Map<Class<? extends DevDbException>, Integer> statusCodes = new ConcurrentHashMap<>();

    public int getHttpStatusCode() {
        return getHttpStatusCode(getClass());
    }

    private static int getHttpStatusCode(Class<? extends DevDbException> clazz) {
        if (statusCodes.containsKey(clazz)) {
            return statusCodes.get(clazz);
        }

        ResponseStatus status = clazz.getAnnotation(ResponseStatus.class);

        int statusCode = status == null
                ? HttpStatus.BAD_REQUEST.value()
                : status.value().value();

        statusCodes.put(clazz, statusCode);

        return statusCode;
    }
}
