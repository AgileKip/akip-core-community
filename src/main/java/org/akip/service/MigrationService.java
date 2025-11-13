package org.akip.service;

import org.akip.domain.ProcessDeployment;
import org.akip.domain.ProcessInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.MigrationResultDTO;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MigrationService {

    private final ProcessInstanceRepository processInstanceRepository;
    private final ProcessDeploymentService processDeploymentService;
    private final RuntimeService runtimeService;
    private final ProcessInstanceService processInstanceService;

    public MigrationService(ProcessInstanceRepository processInstanceRepository, ProcessDeploymentService processDeploymentService, RuntimeService runtimeService, ProcessInstanceService processInstanceService) {
        this.processInstanceRepository = processInstanceRepository;
        this.processDeploymentService = processDeploymentService;
        this.runtimeService = runtimeService;
        this.processInstanceService = processInstanceService;
    }

    public List<Map<String, String>> getRunningCamundaProcessInstances() {
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

    public String migrate(String processDefinitionIdOld, String processDefinitionIdNew, String processInstanceId) {
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

    public List<MigrationResultDTO> migrateBulk(List<Long> processInstancesId) {
        if (processInstancesId == null || processInstancesId.isEmpty()) {
            throw new BadRequestErrorException("ProcessInstances empty");
        }

        List<ProcessInstance> processInstances = processInstanceRepository.findAllById(processInstancesId);
        List<MigrationResultDTO> migrationResultDTOS = new ArrayList<>();

        for (ProcessInstance processInstance : processInstances) {
            String camundaProcessInstanceId = processInstance.getCamundaProcessInstanceId();
            try {
                Optional<ProcessDeployment> latestProcessDeployment = getLatestProcessDeployment(processInstance);
                String camundaProcessDefinitionIdOld = processInstance.getCamundaProcessDefinitionId();
                String camundaProcessDefinitionIdNew = latestProcessDeployment.get().getCamundaProcessDefinitionId();

                if (camundaProcessDefinitionIdOld.equals(camundaProcessDefinitionIdNew)) {
                    migrationResultDTOS.add(new MigrationResultDTO(processInstance.getBusinessKey(), "No migration needed"));
                    continue;
                }

                executeMigration(camundaProcessDefinitionIdOld, camundaProcessDefinitionIdNew, camundaProcessInstanceId);
                migrationResultDTOS.add(new MigrationResultDTO(processInstance.getBusinessKey(), "Migration successful"));
            } catch (BadRequestErrorException ex) {
                migrationResultDTOS.add(new MigrationResultDTO(processInstance.getBusinessKey(), "Migration failed: " + ex.getMessage()));
            }
        }

        return migrationResultDTOS;
    }

    private Optional<ProcessDeployment> getLatestProcessDeployment(ProcessInstance processInstance) {
        return processInstance.getTenant() != null
            ? processDeploymentService.getProcessDeployment(
            processInstance.getTenant().getId(),
            processInstance.getProcessDefinition().getId()
        )
            : processDeploymentService.getProcessDeploymentWithoutTenant(processInstance.getProcessDefinition().getId());
    }

    private void executeMigration(String camundaProcessDefinitionIdOld, String camundaProcessDefinitionIdNew, String camundaProcessInstanceId) {
        try {
            MigrationPlan migrationPlan = runtimeService
                .createMigrationPlan(camundaProcessDefinitionIdOld, camundaProcessDefinitionIdNew)
                .mapEqualActivities()
                .updateEventTriggers()
                .build();
            runtimeService
                .newMigration(migrationPlan)
                .processInstanceIds(camundaProcessInstanceId)
                .skipCustomListeners()
                .skipIoMappings()
                .execute();

            processInstanceService.findAndUpdateProcessInstance(camundaProcessDefinitionIdNew, camundaProcessInstanceId);
        } catch (ProcessEngineException ex) {
            throw new BadRequestErrorException(ex.getMessage());
        }
    }
}
