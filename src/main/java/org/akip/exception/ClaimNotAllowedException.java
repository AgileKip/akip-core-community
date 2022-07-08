package org.akip.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ClaimNotAllowedException extends AbstractThrowableProblem {

    private String candidateGroups;

    public ClaimNotAllowedException(String candidateGroups) {
        super(ErrorConstants.DEFAULT_TYPE, ClaimNotAllowedException.class.getSimpleName(), Status.BAD_REQUEST);
        this.candidateGroups = candidateGroups;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

}
