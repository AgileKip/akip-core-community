package org.akip.web.rest;

import org.akip.recsys.AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig;
import org.akip.recsys.AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 * REST controller for managing {@link org.akip.domain.AkipTaskInstanceRankingContextConfig}.
 */
@RestController
@RequestMapping("/api")
public class AkipTaskInstanceRankingContextPrioritySlaAlgorithmConfigResource {

    private final Logger log = LoggerFactory.getLogger(AkipTaskInstanceRankingContextPrioritySlaAlgorithmConfigResource.class);

    private final AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig;

    public AkipTaskInstanceRankingContextPrioritySlaAlgorithmConfigResource(AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig) {
        this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig = akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig;
    }

    @GetMapping("/akip-task-instance-ranking-context-priority-sla-algorithm-config")
    public AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO getAkipTaskInstanceRankingContextConfig() {
        AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO dto = new AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO();
        dto.setPriorityWeight(this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.getPriorityWeight());
        dto.setSlaWeight(this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.getSlaWeight());
        dto.setContextWeight(this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.getContextWeight());
        return dto;
    }

    @PutMapping("/akip-task-instance-ranking-context-priority-sla-algorithm-config")
    public void updateAkipTaskInstanceRankingContextConfig(
        @RequestBody AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO dto
    ) throws URISyntaxException {
        this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.setPriorityWeight(dto.getPriorityWeight());
        this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.setSlaWeight(dto.getSlaWeight());
        this.akipTaskInstanceRankingContextPrioritySLAAlgorithmConfig.setContextWeight(dto.getContextWeight());
    }

}
