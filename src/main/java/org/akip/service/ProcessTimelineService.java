package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ProcessTimelineService {

    @Autowired
    private ProcessTimelineDefinitionService processTimelineDefinitionService;

    @Autowired
    private ProcessTimelineExpressionService expressionService;

    public ProcessTimelineDTO buildTimeline(String bpmnProcessDefinitionId, IProcessEntity processEntity) {
        List<ProcessTimelineDefinitionDTO> processTimelineDefinitions = processTimelineDefinitionService.getByBpmnProcessDefinitionId(
            bpmnProcessDefinitionId
        );

        ProcessTimelineDefinitionDTO processTimelineDefinition = findTimelineDefinition(processEntity, processTimelineDefinitions);

        return calculateTimeline(processEntity.getProcessInstance(), processTimelineDefinition);
    }

    public ProcessTimelineDTO buildTimeline(String bpmnProcessDefinitionId, ProcessInstanceDTO processInstance) {
        List<ProcessTimelineDefinitionDTO> processTimelineDefinitions = processTimelineDefinitionService.getByBpmnProcessDefinitionId(
            bpmnProcessDefinitionId
        );

        ProcessTimelineDefinitionDTO processTimelineDefinition = findTimelineDefinition(processInstance, processTimelineDefinitions);

        return calculateTimeline(processInstance, processTimelineDefinition);
    }

    private ProcessTimelineDefinitionDTO findTimelineDefinition(
        Object processInstanceOrProcessEntity,
        List<ProcessTimelineDefinitionDTO> processTimelineDefinitions
    ) {
        if (processTimelineDefinitions.isEmpty()) {
            throw new RuntimeException("No timeline found");
        }

        for (ProcessTimelineDefinitionDTO timelineDefinition : processTimelineDefinitions) {
            if (
                timelineDefinition.getConditionExpression() != null &&
                expressionService.evaluateTimeline(processInstanceOrProcessEntity, timelineDefinition.getConditionExpression())
            ) {
                return timelineDefinition;
            }
        }

        return processTimelineDefinitions.get(0);
    }

    private ProcessTimelineDTO calculateTimeline(ProcessInstanceDTO processInstance, ProcessTimelineDefinitionDTO processTimelineDefinition) {
        ProcessTimelineDTO timeline = new ProcessTimelineDTO();
        timeline.setTitle(processTimelineDefinition.getName());

        processTimelineDefinition
            .getItems()
            .forEach(
                timelineItemDefinition -> {
                    timeline
                        .getItems()
                        .add(
                            new ProcessTimelineItemDTO()
                                .title(timelineItemDefinition.getName())
                                .status("WAITING")
                                .icon("clock")
                                .itemDefinition(timelineItemDefinition)
                        );
                }
            );

        calculateStatus(processInstance, timeline);

        return timeline;
    }

    private void calculateStatus(ProcessInstanceDTO processInstance, ProcessTimelineDTO timeline) {
        for (ProcessTimelineItemDTO timelineItem : timeline.getItems()) {
            calculateStatus(processInstance, timelineItem);
            if (timelineItem.getStatus().equals("WAITING") || timelineItem.getStatus().equals("RUNNING")) {
                return;
            }
        }
    }

    private void calculateStatus(ProcessInstanceDTO processInstance, ProcessTimelineItemDTO timelineItem) {
        if (
            expressionService.evaluateCompleteStatusExpression(processInstance, timelineItem.getItemDefinition().getCheckStatusExpression())
        ) {
            timelineItem.setStatus("COMPLETED");
            timelineItem.setIcon("check");
        } else if (
            expressionService.evaluateRunningStatusExpression(processInstance, timelineItem.getItemDefinition().getCheckStatusExpression())
        ) {
            timelineItem.setStatus("RUNNING");
            timelineItem.setIcon("hourglass");
        }
    }

    private void calculateStatusWithoutParameter(ProcessInstance processInstance, ProcessTimelineItemDTO timelineItem)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method checkStatusMethod =
            ProcessTimelineService.class.getMethod(
                    timelineItem.getItemDefinition().getCheckStatusExpression(),
                    ProcessInstance.class,
                    ProcessTimelineItemDTO.class
                );
        checkStatusMethod.invoke(this, processInstance, timelineItem);
    }

    private void calculateStatusWithParameters(ProcessInstance processInstance, ProcessTimelineItemDTO timelineItem)
        throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String expression = timelineItem.getItemDefinition().getCheckStatusExpression().split("\\s+")[0];
        String params = timelineItem.getItemDefinition().getCheckStatusExpression().split("\\s+")[1];
        Method checkStatusMethod =
            ProcessTimelineService.class.getMethod(
                    timelineItem.getItemDefinition().getCheckStatusExpression(),
                    ProcessInstance.class,
                    ProcessTimelineItemDTO.class,
                    String.class
                );
        checkStatusMethod.invoke(this, processInstance, timelineItem, params);
    }
}
