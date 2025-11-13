package org.akip.service;

public interface ProcessCancelationHandler {
    String getProcessDefinitionKey();

    /**
     * Logic to be executed when the process is canceled via API.
     * @param processInstanceId The ID of the process instance that was canceled.
     */
    void onCancelation(Long processInstanceId);
}
