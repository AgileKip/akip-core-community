package org.akip.web.rest;

import org.akip.service.TenantService;
import org.akip.service.dto.TenantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.ProcessDeployment}.
 */
@RestController
@RequestMapping("/api/")
public class ProcessDefinitionTenantController {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionTenantController.class);

    private final TenantService tenantService;

    public ProcessDefinitionTenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/tenants")
    public List<TenantDTO> getTenants(@PathVariable String bpmnProcessDefinitionId) {
        return tenantService.findByProcessDefinitionAndDeploymentActive(bpmnProcessDefinitionId);
    }

}
