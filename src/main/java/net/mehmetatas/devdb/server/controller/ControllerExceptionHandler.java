package net.mehmetatas.devdb.server.controller;

import net.mehmetatas.devdb.exceptions.DevDbException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DevDbException.class)
    @ResponseBody
    public void processAppException(DevDbException ex, HttpServletResponse response) {
        response.setStatus(ex.getHttpStatusCode());
        response.setHeader("X-DevDb-ErrorMessage", ex.getMessage());
    }
}