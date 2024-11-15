package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import tech.jhipster.web.util.HeaderUtil;


/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class AkipExceptionTranslator implements ProblemHandling {

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestErrorException(BadRequestErrorException ex, NativeWebRequest request) {
        return create(
            ex,
            request,
            HeaderUtil.createFailureAlert(HeaderConstants.APPLICATION_NAME, true, "", ex.getErrorKey(), ex.getMessage())
        );
    }

}
