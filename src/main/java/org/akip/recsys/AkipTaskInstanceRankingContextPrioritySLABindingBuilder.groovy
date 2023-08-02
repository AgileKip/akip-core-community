package org.akip.recsys

import org.akip.camunda.CamundaConstants
import org.akip.dao.TaskInstanceSearchDTO
import org.akip.groovy.HqlApi
import org.akip.groovy.JsonApi
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

import javax.persistence.EntityManager

@Component
class AkipTaskInstanceRankingContextPrioritySLABindingBuilder {

    @Value('${akip.projectBasePackage}')
    private String projectBasePackage;

    @Autowired
    BeanFactory beanFactory

    @Autowired
    HqlApi hqlApi

    JsonApi jsonApi = new JsonApi()

    @Autowired
    org.akip.groovy.MessageApi messageApi

    @Autowired
    Environment env

    @Autowired
    EntityManager entityManager

    Binding buildBinding() {
        Binding binding = new Binding();
        binding.setVariable("env", env)
        binding.setVariable("hqlApi", hqlApi)
        binding.setVariable("jsonApi", jsonApi)
        binding.setVariable("messageApi", messageApi)
        binding.setVariable("entityManager", entityManager)
        return binding;
    }

    Binding buildBindingFromTask(TaskInstanceSearchDTO task) {
        if (task.getDomainEntityname() == null || task.getDomainEntityId() == null) {
            return buildBindingFromProcessInstance(task.processInstanceId);
        }

        return buildBindingFromProcessEntity(task.domainEntityname, task.domainEntityId)
    }

    Binding buildBindingFromProcessEntity(String entityName, Long entityId) {
        Binding binding = Binding binding = buildBinding();
        if (!entityName || !entityId) {
            return binding
        }
        def processEntityLoadedFromDB = loadObjectFromDB(processEntity)
        binding.setVariable(CamundaConstants.PROCESS_ENTITY, processEntityLoadedFromDB)
        return binding;
    }

    Binding buildBindingFromProcessInstance(Long processInstanceId) {
        Binding binding = buildBinding();
        if (!processInstanceId) {
            return binding
        }
        def processInstanceLoadedFromDB = loadObjectFromDB(org.akip.domain.ProcessInstance.class.simpleName, processInstanceId)
        binding.setVariable(CamundaConstants.PROCESS_INSTANCE, processInstanceLoadedFromDB)
        return binding;
    }


    def loadObjectFromDB(String entityName, Long entityId) {
        return getObjectServiceComponent(entityName)
                .findOne(entityId)
                .get()
    }

    def getObjectServiceComponent(String entityName) {
        def serviceBeanName = entityName
                .uncapitalize()
                .concat('Service')
        return beanFactory.getBean(serviceBeanName)
    }
}
