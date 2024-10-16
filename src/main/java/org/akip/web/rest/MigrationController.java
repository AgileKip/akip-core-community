package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.service.ProcessInstanceService;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MigrationController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessInstanceService processInstanceService;

    @GetMapping("/migration/process-instances/")
    public List<Map<String, String>> getAllProcessInstances() {
        List<Map<String, String>> results = new ArrayList<>();
        runtimeService
            .createProcessInstanceQuery()
            .active()
            .list()
            .forEach(
                processInstance -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("processDefinitionId", processInstance.getProcessDefinitionId());
                    map.put("processInstanceId", processInstance.getProcessInstanceId());
                    map.put("businessKey", processInstance.getBusinessKey());
                    map.put("caseInstanceId", processInstance.getCaseInstanceId());
                    results.add(map);
                }
            );
        return results;
    }

    @GetMapping("/migration/migrate/{processDefinitionIdOld}/{processDefinitionIdNew}/{processInstanceId}")
    public String migrate(
        @PathVariable("processDefinitionIdOld") String processDefinitionIdOld,
        @PathVariable("processDefinitionIdNew") String processDefinitionIdNew,
        @PathVariable("processInstanceId") String processInstanceId
    ) {
        if (
            processDefinitionIdOld.isEmpty() ||
            processDefinitionIdOld.equalsIgnoreCase("null") ||
            processDefinitionIdOld.equalsIgnoreCase("undefined")
        ) {
            throw new BadRequestErrorException("ProcessDefinitionIdOld empty");
        }
        if (
            processDefinitionIdNew.isEmpty() ||
            processDefinitionIdNew.equalsIgnoreCase("null") ||
            processDefinitionIdNew.equalsIgnoreCase("undefined")
        ) {
            throw new BadRequestErrorException("ProcessDefinitionIdNew empty");
        }
        if (processInstanceId.isEmpty() || processInstanceId.equalsIgnoreCase("null") || processInstanceId.equalsIgnoreCase("undefined")) {
            throw new BadRequestErrorException("ProcessInstanceId empty");
        }

        try {
            MigrationPlan migrationPlan = runtimeService
                .createMigrationPlan(processDefinitionIdOld, processDefinitionIdNew)
                .mapEqualActivities()
                .updateEventTriggers()
                .build();
            runtimeService
                .newMigration(migrationPlan)
                .processInstanceIds(processInstanceId)
                .skipCustomListeners()
                .skipIoMappings()
                .execute();

            processInstanceService.findAndUpdateProcessInstance(processDefinitionIdNew, processInstanceId);
            return processDefinitionIdNew;
        } catch (ProcessEngineException ex) {
            throw new BadRequestErrorException(ex.getMessage());
        }
    }
}
