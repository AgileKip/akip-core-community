package org.akip.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.util.HashMap;
import java.util.Map;

public class BadRequestErrorException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String errorKey;

    private final String[] params;

    public BadRequestErrorException(String errorKey, String... params) {
        super(ErrorConstants.DEFAULT_TYPE, errorKey, Status.BAD_REQUEST, null, null, null, getErrorParameters(errorKey, params));
        this.params = params;
        this.errorKey = errorKey;
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
