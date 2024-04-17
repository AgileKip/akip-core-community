package org.akip.recsys;

import org.akip.dao.TaskInstanceSearchDTO;
import org.akip.domain.AkipTaskInstanceRankingContextConfig;
import org.akip.repository.AkipTaskInstanceRankingContextConfigRepository;
import org.akip.util.BigDecimalUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AkipTaskInstanceRankingContextPrioritySLAAlgorithm implements AkipTaskInstanceRakingAlgorithmInterface {

    public static final String PRIORITY_RAW_RANK = "priority.RawRank";
    public static final String PRIORITY_RANK = "priority.Rank";
    public static final String TASK_SLA_RAW_RANK = "sla.taskSlaRawRank";
    public static final String TASK_SLA_RANK = "sla.taskSlaRank";
    public static final String PROCESS_TASK_SLA_RAW_RANK = "sla.processTaskSlaRawRank";
    public static final String PROCESS_TASK_SLA_RANK = "sla.processTaskSlaRank";
    public static final String SLA_RAW_RANK = "sla.RawRank";
    public static final String SLA_RANK = "sla.Rank";
    public static final String TASK_SLA = "sla.taskSla";
    public static final String PROCESS_TASK_SLA = "sla.processTaskSla";
    public static final String PROPERTY_TASK_SLA = "akip.taskSla";
    public static final String PROPERTY_PROCESS_TASK_SLA = "akip.processTaskSla";
    public static final String CONTEXT_RAW_RANK = "context.RawRank";
    public static final String CONTEXT_RANK = "context.Rank";
    public static final String CONTEXT_VALUE = "context.Value";
    private final AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig config;
    private final AkipTaskInstanceRankingContextPrioritySLAGroovyService akipTaskInstanceRankingContextPrioritySLAGroovyService;

    private final AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository;

    public AkipTaskInstanceRankingContextPrioritySLAAlgorithm(AkipTaskInstanceRankingContextPrioritySLAAlgorithmConfig config, AkipTaskInstanceRankingContextPrioritySLAGroovyService akipTaskInstanceRankingContextPrioritySLAGroovyService, AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository) {
        this.config = config;
        this.akipTaskInstanceRankingContextPrioritySLAGroovyService = akipTaskInstanceRankingContextPrioritySLAGroovyService;
        this.akipTaskInstanceRankingContextConfigRepository = akipTaskInstanceRankingContextConfigRepository;
    }

    @Override
    public String getName() {
        return "AKIP Context/Priority/SLA Algorithm ";
    }

    @Override
    public void buildRanking(List<TaskInstanceSearchDTO> tasks) {
        List<TaskInstanceSearchDTO> openTasks = tasks
                .stream()
                .filter(taskInstanceSearchDTO -> taskInstanceSearchDTO.isOpen())
                .collect(Collectors.toList());
        buildPriorityRanking(openTasks);
        buildSlaRanking(openTasks);
        buildContextRanking(openTasks);
        buildFinalRanking(openTasks);
    }


    private void buildPriorityRanking(List<TaskInstanceSearchDTO> tasks) {
        if (BigDecimal.ZERO.equals(config.getPriorityWeight())) {
            tasks.forEach(task -> {
                task.getRankData().put(PRIORITY_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(PRIORITY_RANK, BigDecimal.ZERO);
            });
            return;
        }

        BigDecimal totalPriority = tasks
            .stream()
            .map(task -> new BigDecimal(task.getPriority()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPriority.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put(PRIORITY_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(PRIORITY_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put(PRIORITY_RAW_RANK, BigDecimalUtil.divide(task.getPriority(), totalPriority));
            task.getRankData().put(PRIORITY_RANK, BigDecimalUtil.divide(task.getPriority(), totalPriority).multiply(config.getPriorityWeight()));
        });
    }

    private void buildSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        buildTaskSlaRanking(tasks);
        buildProcessTaskSlaRanking(tasks);
        tasks.forEach(this::buildSlaRanking);
    }

    private void buildSlaRanking(TaskInstanceSearchDTO task) {
        BigDecimal half = new BigDecimal("0.5");
        BigDecimal taskSlaRawRank = ((BigDecimal) task.getRankData().get(TASK_SLA_RAW_RANK)).multiply(half);
        BigDecimal taskSlaRank = ((BigDecimal) task.getRankData().get(TASK_SLA_RANK)).multiply(half);
        BigDecimal processTaskSlaRawRank = ((BigDecimal) task.getRankData().get(PROCESS_TASK_SLA_RAW_RANK)).multiply(half);
        BigDecimal processTaskSlaRank = ((BigDecimal) task.getRankData().get(PROCESS_TASK_SLA_RANK)).multiply(half);
        task.getRankData().put(SLA_RAW_RANK, taskSlaRawRank.add(processTaskSlaRawRank));
        task.getRankData().put(SLA_RANK, taskSlaRank.add(processTaskSlaRank));
    }

    private void buildTaskSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        if (BigDecimal.ZERO.equals(config.getSlaWeight())) {
            tasks.forEach(task -> {
                task.getRankData().put(TASK_SLA_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(TASK_SLA_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(this::buildTaskSla);

        BigDecimal totalTaskSla = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get(TASK_SLA))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalTaskSla.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put(TASK_SLA_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(TASK_SLA_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put(TASK_SLA_RAW_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(TASK_SLA), totalTaskSla));
            task.getRankData().put(TASK_SLA_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(TASK_SLA), totalTaskSla).multiply(config.getSlaWeight()));
        });
    }

    private void buildTaskSla(TaskInstanceSearchDTO task) {
        String akipTaskSla = task.getProps().get(PROPERTY_TASK_SLA);
        if (akipTaskSla == null) {
            task.getRankData().put(TASK_SLA, BigDecimal.ZERO);
            return;
        }

        Duration taskAgeAsDuration = Duration.between(task.getCreateDate(), Instant.now());
        Duration taskSlaAsDuration = Duration.parse(akipTaskSla);
        BigDecimal taskAgeAsBigDecimal = new BigDecimal(taskAgeAsDuration.toMillis());
        BigDecimal taskSlaAsBigDecimal = new BigDecimal(taskSlaAsDuration.toMillis());
        task.getRankData().put(TASK_SLA, BigDecimalUtil.divide(taskAgeAsBigDecimal, taskSlaAsBigDecimal));
    }

    private void buildProcessTaskSlaRanking(List<TaskInstanceSearchDTO> tasks) {
        if (BigDecimal.ZERO.equals(config.getSlaWeight())) {
            tasks.forEach(task -> {
                task.getRankData().put(PROCESS_TASK_SLA_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(PROCESS_TASK_SLA_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(this::buildProcessTaskSla);

        BigDecimal totalProcessTaskSla = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get(PROCESS_TASK_SLA))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalProcessTaskSla.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put(PROCESS_TASK_SLA_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(PROCESS_TASK_SLA_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put(PROCESS_TASK_SLA_RAW_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(PROCESS_TASK_SLA), totalProcessTaskSla));
            task.getRankData().put(PROCESS_TASK_SLA_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(PROCESS_TASK_SLA), totalProcessTaskSla).multiply(config.getSlaWeight()));
        });
    }

    private void buildProcessTaskSla(TaskInstanceSearchDTO task) {
        String processTaskSla = task.getProps().get(PROPERTY_PROCESS_TASK_SLA);
        if (processTaskSla == null) {
            task.getRankData().put(PROCESS_TASK_SLA, BigDecimal.ZERO);
            return;
        }

        Duration processInstanceAgeAsDuration = Duration.between(task.getProcessInstanceStartDate(), LocalDateTime.now());
        Duration processTaskSlaAsDuration = Duration.parse(processTaskSla);
        BigDecimal processInstanceAgeAsBigDecimal = new BigDecimal(processInstanceAgeAsDuration.toMillis());
        BigDecimal processTaskSlaAsBigDecimal = new BigDecimal(processTaskSlaAsDuration.toMillis());
        task.getRankData().put(PROCESS_TASK_SLA, BigDecimalUtil.divide(processInstanceAgeAsBigDecimal, processTaskSlaAsBigDecimal));
    }


    private void buildContextRanking(List<TaskInstanceSearchDTO> tasks) {
        if (BigDecimal.ZERO.equals(config.getContextWeight())) {
            tasks.forEach(task -> {
                task.getRankData().put(CONTEXT_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(CONTEXT_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(this::buildContextValue);

        BigDecimal totalContext = tasks
            .stream()
            .map(task -> (BigDecimal) task.getRankData().get(CONTEXT_VALUE))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalContext.equals(BigDecimal.ZERO)) {
            tasks.forEach(task -> {
                task.getRankData().put(CONTEXT_RAW_RANK, BigDecimal.ZERO);
                task.getRankData().put(CONTEXT_RANK, BigDecimal.ZERO);
            });
            return;
        }

        tasks.forEach(task -> {
            task.getRankData().put(CONTEXT_RAW_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(CONTEXT_VALUE), totalContext));
            task.getRankData().put(CONTEXT_RANK, BigDecimalUtil.divide((BigDecimal) task.getRankData().get(CONTEXT_VALUE), totalContext).multiply(config.getContextWeight()));
        });
    }

    private void buildContextValue(TaskInstanceSearchDTO task) {
        Optional<AkipTaskInstanceRankingContextConfig> taskInstanceRankingContextConfig = akipTaskInstanceRankingContextConfigRepository.findByProcessDefinitionBpmnProcessDefinitionId(task.getProcessDefinitionBpmnProcessDefinitionId());
        task.getRankData().put(CONTEXT_VALUE, akipTaskInstanceRankingContextPrioritySLAGroovyService.buildContextValue(taskInstanceRankingContextConfig, task));
    }

    private void buildFinalRanking(List<TaskInstanceSearchDTO> tasks) {
        tasks.forEach(this::buildFinalRanking);

        int[] i = { 1 };
        tasks.stream()
                .sorted(Comparator.comparing(TaskInstanceSearchDTO::getRank)
                .reversed())
                .forEach(taskInstanceSearchDTO -> taskInstanceSearchDTO.setRankInteger(i[0]++));
    }

    private void buildFinalRanking(TaskInstanceSearchDTO task) {
        BigDecimal priorityRank = (BigDecimal) task.getRankData().get(PRIORITY_RANK);
        BigDecimal slaRank = (BigDecimal) task.getRankData().get(SLA_RANK);
        BigDecimal contextRank = (BigDecimal) task.getRankData().get(CONTEXT_RANK);
        BigDecimal totalRanks = BigDecimalUtil.sum(priorityRank, slaRank, contextRank);
        BigDecimal finalRank = BigDecimalUtil.divide(totalRanks, config.getSumWeights());
        task.setRank(finalRank.setScale(4, RoundingMode.HALF_UP));
    }

}
