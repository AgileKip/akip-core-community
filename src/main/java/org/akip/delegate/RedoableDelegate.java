package org.akip.delegate;


import org.akip.service.dto.TaskInstanceDTO;

public interface RedoableDelegate {
    void redo(TaskInstanceDTO taskInstance);
}
