package org.akip.service.mapper;

import org.akip.domain.TaskInstance;
import org.akip.service.dto.TaskInstanceDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link TaskInstance} and its DTO {@link TaskInstanceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessDefinitionMapper.class, ProcessInstanceMapper.class, TaskDefinitionMapper.class })
public interface TaskInstanceMapper extends EntityMapper<TaskInstanceDTO, TaskInstance> {

    final String delimiter = ",";

    @Mapping(target = "processDefinition", source = "processDefinition", qualifiedByName = "name")
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "businessKey")
    @Mapping(target = "taskDefinition", source = "taskDefinition")
    TaskInstanceDTO toDto(TaskInstance s);

    @Named("loadTaskContext")
    @Mapping(target = "processDefinition", source = "processDefinition", qualifiedByName = "loadTaskContext")
    @Mapping(target = "processInstance", source = "processInstance", qualifiedByName = "loadTaskContext")
    TaskInstanceDTO toDTOLoadTaskContext(TaskInstance s);

    default List<String> stringToList(String string) {
        if (StringUtils.isBlank(string)) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        Arrays
            .stream(string.split(delimiter))
            .forEach(
                computedCandidateGroup -> {
                    if (computedCandidateGroup.length() > 0) {
                        list.add(computedCandidateGroup);
                    }
                }
            );
        return list;
    }

    default String listToString(List<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        return delimiter + list.stream().collect(Collectors.joining(delimiter)) + delimiter;
    }
}
