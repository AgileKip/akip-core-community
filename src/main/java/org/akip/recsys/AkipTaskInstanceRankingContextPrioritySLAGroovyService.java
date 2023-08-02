package org.akip.recsys;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.akip.dao.TaskInstanceSearchDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AkipTaskInstanceRankingContextPrioritySLAGroovyService {

    private final AkipTaskInstanceRankingContextPrioritySLABindingBuilder bindingBuilder;

    public AkipTaskInstanceRankingContextPrioritySLAGroovyService(AkipTaskInstanceRankingContextPrioritySLABindingBuilder bindingBuilder) {
        this.bindingBuilder = bindingBuilder;
    }

    public BigDecimal buildContextValue(Optional<String> contextValueExpression, TaskInstanceSearchDTO task) {
        if (contextValueExpression.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Binding binding = bindingBuilder.buildBindingFromTask(task);
        GroovyShell shell = new GroovyShell(binding);
        String expression = contextValueExpression.get();
        if (!expression.contains("\"\"\"")) {
            expression = "\"\"\"" + expression + "\"\"\"";
        }
        return new BigDecimal(shell.evaluate(expression).toString());
    }

}
