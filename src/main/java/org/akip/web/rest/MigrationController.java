package org.akip.web.rest;

import org.akip.service.MigrationService;
import org.akip.service.dto.MigrationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MigrationController {

    @Autowired
    private MigrationService migrationService;

    @GetMapping("/migration/process-instances/")
    public List<Map<String, String>> getAllProcessInstances() {
        return migrationService.getRunningCamundaProcessInstances();
    }

    @GetMapping("/migration/migrate/{processDefinitionIdOld}/{processDefinitionIdNew}/{processInstanceId}")
    public String migrate(
            @PathVariable("processDefinitionIdOld") String processDefinitionIdOld,
            @PathVariable("processDefinitionIdNew") String processDefinitionIdNew,
            @PathVariable("processInstanceId") String processInstanceId
    ) {
        return migrationService.migrate(processDefinitionIdOld, processDefinitionIdNew, processInstanceId);
    }

    @PostMapping("/migration/migrate-in-bulk")
    public List<MigrationResultDTO> migrateBatch(@RequestBody List<Long> processInstancesId) {
        return migrationService.migrateBulk(processInstancesId);
    }
}
