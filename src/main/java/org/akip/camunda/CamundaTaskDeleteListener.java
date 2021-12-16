package org.akip.camunda;

import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class CamundaTaskDeleteListener implements TaskListener {

    private final TaskInstanceRepository taskInstanceRepository;

    public CamundaTaskDeleteListener(TaskInstanceRepository taskInstanceRepository) {
        this.taskInstanceRepository = taskInstanceRepository;
    }

    public void notify(DelegateTask delegateTask) {
        Optional<TaskInstance> optionalTaskInstance = taskInstanceRepository.findByTaskId(delegateTask.getId());

        if (optionalTaskInstance.isPresent()) {
            TaskInstance taskInstance = optionalTaskInstance.get();
            taskInstance.setStatus(StatusTaskInstance.COMPLETED);
            taskInstance.endTime(Instant.now());
            taskInstanceRepository.save(taskInstance);
        }
    }
}