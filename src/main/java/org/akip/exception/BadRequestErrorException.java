package org.akip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;

import java.util.HashMap;
import java.util.Map;

public class BadRequestErrorException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    private final String errorKey;

    private final String[] params;

    public BadRequestErrorException(String errorKey, String... params) {
        super(
                HttpStatus.BAD_REQUEST,
                ProblemDetailWithCause.ProblemDetailWithCauseBuilder.instance()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withType(ErrorConstants.DEFAULT_TYPE)
                        .withTitle(errorKey)
                        .withProperty("message", "error." + errorKey)
                        .withProperty("params", params)
                        .build(),
                null
        );
        this.errorKey = errorKey;
        this.params = params;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String[] getParams() {
        return params;
    }

    private static Map<String, Object> getErrorParameters(String errorKey, String[] params) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", errorKey);

        if (params.length == 0) {
            return parameters;
        }

        if (params.length == 1) {
            parameters.put("param", params[0]);
            return parameters;
        }

        for (int i = 0; i < params.length; i++) {
            parameters.put("param" + i, params[i]);
        }

        return parameters;
    }
}
