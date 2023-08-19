package org.akip.recsys;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig {

    public AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig(Environment env) {
        if (env.getProperty("akip.recsys.rankingContextPrioritySLA.priorityWeight") != null) {
            this.priorityWeight = new BigDecimal(env.getProperty("akip.recsys.rankingContextPrioritySLA.priorityWeight"));
        }

        if (env.getProperty("akip.recsys.rankingContextPrioritySLA.slaWeight") != null) {
            this.slaWeight = new BigDecimal(env.getProperty("akip.recsys.rankingContextPrioritySLA.slaWeight"));
        }

        if (env.getProperty("akip.recsys.rankingContextPrioritySLA.contextWeight") != null) {
            this.contextWeight = new BigDecimal(env.getProperty("akip.recsys.rankingContextPrioritySLA.contextWeight"));
        }

    }


    private BigDecimal priorityWeight = BigDecimal.ONE;
    private BigDecimal slaWeight = BigDecimal.ONE;
    private BigDecimal contextWeight = BigDecimal.ONE;

    public BigDecimal getPriorityWeight() {
        return priorityWeight;
    }

    public BigDecimal getSlaWeight() {
        return slaWeight;
    }

    public BigDecimal getContextWeight() {
        return contextWeight;
    }

    public BigDecimal getSumWeights() {
        return priorityWeight.add(slaWeight).add(contextWeight);
    }
}
