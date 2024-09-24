package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;
import tech.jhipster.web.util.HeaderUtil;


/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class AkipExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleBadRequestErrorException(BadRequestErrorException ex, NativeWebRequest request) {
        ProblemDetailWithCause pdCause = (ProblemDetailWithCause) ex.getBody();
        return handleExceptionInternal((Exception) ex, pdCause, buildHeaders(ex), HttpStatusCode.valueOf(pdCause.getStatus()), request);
    }

    private HttpHeaders buildHeaders(BadRequestErrorException ex) {
        return HeaderUtil.createFailureAlert(HeaderConstants.APPLICATION_NAME, true, "", ex.getErrorKey(), ex.getMessage());
    }

}
