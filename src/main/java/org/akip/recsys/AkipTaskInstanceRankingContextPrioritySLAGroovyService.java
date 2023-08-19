package org.akip.recsys;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.akip.dao.TaskInstanceSearchDTO;
import org.akip.domain.AkipTaskInstanceRankingContextConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AkipTaskInstanceRankingContextPrioritySLAGroovyService {

    private final Logger log = LoggerFactory.getLogger(AkipTaskInstanceRankingContextPrioritySLAGroovyService.class);

    private final AkipTaskInstanceRankingContextPrioritySLABindingBuilder bindingBuilder;

    public AkipTaskInstanceRankingContextPrioritySLAGroovyService(AkipTaskInstanceRankingContextPrioritySLABindingBuilder bindingBuilder) {
        this.bindingBuilder = bindingBuilder;
    }

    public BigDecimal buildContextValue(Optional<AkipTaskInstanceRankingContextConfig> akipTaskInstanceRankingContextConfig, TaskInstanceSearchDTO task) {
        if (akipTaskInstanceRankingContextConfig.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Binding binding = bindingBuilder.buildBindingFromTask(task);
        GroovyShell shell = new GroovyShell(binding);
        String expression = akipTaskInstanceRankingContextConfig.get().getContextValueExpression();
        try {
            return new BigDecimal(shell.evaluate(expression).toString());
        } catch (Exception e) {
            log.error("Error calculating context value: " + e);
            return BigDecimal.ZERO;
        }
    }

}
