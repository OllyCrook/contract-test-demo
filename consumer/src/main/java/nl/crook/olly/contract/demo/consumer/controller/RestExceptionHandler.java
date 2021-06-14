package nl.crook.olly.contract.demo.consumer.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<Object> handleFeignException(final FeignException e) {
        log.error(e.getMessage() + " calling " + e.request().httpMethod() + " " + e.request().url().toString(), e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllOtherExceptions(Exception e) {
        String message = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e) : e.getMessage();
        log.error(message, e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * The default implementation in ResponseEntityExceptionHandler does not log the error, so override it and log the error
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String causeClassName = e.getCause() == null ? "null" : e.getCause().getClass().getSimpleName();
        final String message = "Exception=" + e.getClass().getSimpleName() + ", message=" + e.getMessage() + ", cause=" + causeClassName + ", HttpStatus=" + status;
        log.error(message, e);
        return new ResponseEntity<>(status);
    }
}
