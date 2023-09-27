package org.akip.recsys;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfigDTO {

    private BigDecimal priorityWeight = BigDecimal.ONE;
    private BigDecimal slaWeight = BigDecimal.ONE;
    private BigDecimal contextWeight = BigDecimal.ONE;

    public BigDecimal getPriorityWeight() {
        return priorityWeight;
    }

    public void setPriorityWeight(BigDecimal priorityWeight) {
        this.priorityWeight = priorityWeight;
    }

    public BigDecimal getSlaWeight() {
        return slaWeight;
    }

    public void setSlaWeight(BigDecimal slaWeight) {
        this.slaWeight = slaWeight;
    }

    public BigDecimal getContextWeight() {
        return contextWeight;
    }

    public void setContextWeight(BigDecimal contextWeight) {
        this.contextWeight = contextWeight;
    }

    public BigDecimal getSumWeights() {
        return priorityWeight.add(slaWeight).add(contextWeight);
    }
}
