package org.akip.recsys;

import org.akip.dao.TaskInstanceSearchDTO;
import org.akip.util.BigDecimalUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AkipTaskInstanceRankingContextPrioritySLAAlgorithm implements AkipTaskInstanceRakingAlgorithmInterface {

    private final AkipTaskInstanceRankingContextPrioritySLAGroovyService akipTaskInstanceRankingContextPrioritySLAGroovyService;

    public AkipTaskInstanceRankingContextPrioritySLAAlgorithm(AkipTaskInstanceRankingContextPrioritySLAGroovyService akipTaskInstanceRankingContextPrioritySLAGroovyService) {
        this.akipTaskInstanceRankingContextPrioritySLAGroovyService = akipTaskInstanceRankingContextPrioritySLAGroovyService;
    }

    @Override
    public String getName() {
        return "AKIP Context/Priority/SLA Algorithm ";
    }

    @Override
    public void buildRanking(List<TaskInstanceSearchDTO> tasks) {
        buildPriorityRanking(tasks);
        buildSlaRanking(tasks);
        buildContextRanking(tasks);
        buildFinalRanking(tasks);
    }

    private AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig getConfig() {
        return new AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig();
    }



    private void buildPriorityRanking(List<TaskInstanceSearchDTO> tasks) {
        BigDecimal totalPriority = tasks
            .stream()
            .map(task -> new BigDecimal(task.getPriority()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPriority.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put("priorityRawRank", BigDecimal.ZERO);
                task.getRankData().put("priorityRank", BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put("priorityRawRank", BigDecimalUtil.divide(task.getPriority(), totalPriority));
            task.getRankData().put("priorityRank", BigDecimalUtil.divide(task.getPriority(), totalPriority).multiply(getConfig().getPriorityWeight()));
        });
    }

    private void buildSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        buildTaskSlaRanking(tasks);
        buildProcessTaskSlaRanking(tasks);
        tasks.forEach(this::buildSlaRanking);
    }

    private void buildSlaRanking(TaskInstanceSearchDTO task) {
        BigDecimal half = new BigDecimal("0.5");
        BigDecimal taskSlaRawRank = ((BigDecimal) task.getRankData().get("taskSlaRank")).multiply(half);
        BigDecimal taskSlaRank = ((BigDecimal) task.getRankData().get("taskSlaRank")).multiply(half);
        BigDecimal processTaskSlaRawRank = ((BigDecimal) task.getRankData().get("processTaskSlaRawRank")).multiply(half);
        BigDecimal processTaskSlaRank = ((BigDecimal) task.getRankData().get("processTaskSlaRank")).multiply(half);
        task.getRankData().put("slaRawRank", taskSlaRawRank.add(processTaskSlaRawRank));
        task.getRankData().put("slaRank", taskSlaRank.add(processTaskSlaRank));
    }

    private void buildTaskSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        tasks.forEach(this::buildTaskSla);

        BigDecimal totalTaskSla = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get("taskSla"))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalTaskSla.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put("taskSlaRawRank", BigDecimal.ZERO);
                task.getRankData().put("taskSlaRank", BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put("taskSlaRawRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("taskSla"), totalTaskSla));
            task.getRankData().put("taskSlaRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("taskSla"), totalTaskSla).multiply(getConfig().getSlaWeight()));
        });
    }

    private void buildTaskSla(TaskInstanceSearchDTO task) {
        String akipTaskSla = (String) task.getProps().get("akip.taskSla");
        if (akipTaskSla == null) {
            task.getRankData().put("taskSla", BigDecimal.ZERO);
            return;
        }

        Duration taskAgeAsDuration = Duration.between(task.getCreateDate(), Instant.now());
        Duration taskSlaAsDuration = Duration.parse(akipTaskSla);
        BigDecimal taskAgeAsBigDecimal = new BigDecimal(taskAgeAsDuration.toMillis());
        BigDecimal taskSlaAsBigDecimal = new BigDecimal(taskSlaAsDuration.toMillis());
        task.getRankData().put("taskSla", BigDecimalUtil.divide(taskAgeAsBigDecimal, taskSlaAsBigDecimal));
    }

    private void buildProcessTaskSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        tasks.forEach(this::buildProcessTaskSla);

        BigDecimal totalProcessTaskSla = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get("processTaskSla"))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalProcessTaskSla.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put("processTaskSlaRawRank", BigDecimal.ZERO);
                task.getRankData().put("processTaskSlaRank", BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put("processTaskSlaRawRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("processTaskSla"), totalProcessTaskSla));
            task.getRankData().put("processTaskSlaRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("processTaskSla"), totalProcessTaskSla).multiply(getConfig().getSlaWeight()));
        });
    }

    private void buildProcessTaskSla(TaskInstanceSearchDTO task) {
        String processTaskSla = (String) task.getProps().get("akip.processTaskSla");
        if (processTaskSla == null) {
            task.getRankData().put("processTaskSla", BigDecimal.ZERO);
            return;
        }

        Duration processInstanceAgeAsDuration = Duration.ZERO; //TODO
        Duration processTaskSlaAsDuration = Duration.parse((String) task.getProps().get("akip.processTaskSla"));
        BigDecimal processInstanceAgeAsBigDecimal = new BigDecimal(processInstanceAgeAsDuration.toMillis());
        BigDecimal processTaskSlaAsBigDecimal = new BigDecimal(processTaskSlaAsDuration.toMillis());
        task.getRankData().put("processTaskSla", BigDecimalUtil.divide(processInstanceAgeAsBigDecimal, processTaskSlaAsBigDecimal));
    }


    private void buildContextRanking(List<TaskInstanceSearchDTO> tasks) {
        tasks.forEach(this::buildContextValue);

        BigDecimal totalContext = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get("contextValue"))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalContext.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put("contextRawRank", BigDecimal.ZERO);
                task.getRankData().put("contextRank", BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put("contextRawRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("contextValue"), totalContext));
            task.getRankData().put("contextRank", BigDecimalUtil.divide((BigDecimal) task.getRankData().get("contextValue"), totalContext).multiply(getConfig().getContextWeight()));
        });
    }

    private void buildContextValue(TaskInstanceSearchDTO task) {
        Optional<String> contextValueExpression = getContextValueExpression(task.getProcessDefinitionBpmnProcessDefinitionId());
        task.getRankData().put("contextValue", akipTaskInstanceRankingContextPrioritySLAGroovyService.buildContextValue(contextValueExpression, task));
    }

    private Optional<String> getContextValueExpression(String processDefinitionBpmnProcessDefinitionId) {
        if ("ProcessXPTO1".equals(processDefinitionBpmnProcessDefinitionId)) {
            return Optional.of("1");
        }

        if ("ProcessXPTO2".equals(processDefinitionBpmnProcessDefinitionId)) {
            return Optional.of("2");
        }

        if ("ProcessXPTO3".equals(processDefinitionBpmnProcessDefinitionId)) {
            return Optional.of("3");
        }

        return Optional.empty();
    }

    private void buildFinalRanking(List<TaskInstanceSearchDTO> tasks) {
        tasks.forEach(this::buildFinalRanking);
    }

    private void buildFinalRanking(TaskInstanceSearchDTO task) {
        BigDecimal priorityRank = (BigDecimal) task.getRankData().get("priorityRank");
        BigDecimal slaRank = (BigDecimal) task.getRankData().get("slaRank");
        BigDecimal contextRank = (BigDecimal) task.getRankData().get("contextRank");
        BigDecimal totalRanks = BigDecimalUtil.sum(priorityRank, slaRank, contextRank);
        BigDecimal finalRank = BigDecimalUtil.divide(totalRanks, getConfig().getSumWeights());
        task.setRank(finalRank);
    }

}
