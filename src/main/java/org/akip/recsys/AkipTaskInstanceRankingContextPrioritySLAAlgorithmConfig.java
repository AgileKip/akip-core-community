package org.akip.recsys;

import java.math.BigDecimal;

public class AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig {
    private String rankingAlgorithmComponent = AkipTaskInstanceRankingContextPrioritySLAAlgorithm.class.getSimpleName().substring(0, 1).toLowerCase() + AkipTaskInstanceRankingContextPrioritySLAAlgorithm.class.getSimpleName().substring(1);
    private BigDecimal priorityWeight = BigDecimal.ONE;
    private BigDecimal slaWeight = BigDecimal.ONE;
    private BigDecimal contextWeight = BigDecimal.ONE;

    public String getRankingAlgorithmComponent() {
        return rankingAlgorithmComponent;
    }

    public void setRankingAlgorithmComponent(String rankingAlgorithmComponent) {
        this.rankingAlgorithmComponent = rankingAlgorithmComponent;
    }

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
