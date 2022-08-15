package org.akip.groovy

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

import javax.persistence.EntityManager

@Component
class BindingBuilder {

    @Autowired
    BeanFactory beanFactory

    @Autowired
    HqlApi hqlApi

    JsonApi jsonApi = new JsonApi()

    @Autowired
    MessageApi messageApi

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

    Binding buildBindingFromProcessEntity(def processEntity) {
        Binding binding = new Binding();
        def processEntityLoadedFromDB = loadObjectFromDB(processEntity)
        binding.setVariable(CamundaConstants.PROCESS_ENTITY, processEntityLoadedFromDB)
        binding.setVariable("env", env)
        binding.setVariable("hqlApi", hqlApi)
        binding.setVariable("jsonApi", jsonApi)
        binding.setVariable("messageApi", messageApi)
        return binding;
    }

    Binding buildBindingFromProcessInstance(def processInstance) {
        Binding binding = new Binding();
        def processInstanceLoadedFromDB = loadObjectFromDB(processInstance)
        binding.setVariable(CamundaConstants.PROCESS_INSTANCE, processInstanceLoadedFromDB)
        binding.setVariable("env", env)
        binding.setVariable("hqlApi", hqlApi)
        binding.setVariable("jsonApi", jsonApi)
        binding.setVariable("messageApi", messageApi)
        return binding;
    }

    Binding buildBinding(String processEntityName, Long processEntityId) {
        def processEntity = Class.forName("${TravelPlanEnterpriseApp.class.packageName}.service.dto.${processEntityName}DTO")
                .getDeclaredConstructor()
                .newInstance()
        processEntity.id = processEntityId
        return buildBindingFromProcessEntity(processEntity)
    }


    def loadObjectFromDB(def processInstance) {
        if (!processInstance.id) {
           return processInstance
        }

        return getObjectMapperComponent(processInstance)
            .toDto(getObjectRepositoryComponent(processInstance).findById(processInstance.id).get())
    }

    JpaRepository<?,?> getObjectRepositoryComponent(def processInstance) {
        def repositoryBeanName = processInstance.class.name
                .replace('service.dto', 'repository')
                .replace('DTO', 'Repository')
        return beanFactory.getBean(Class.forName(repositoryBeanName))
    }

    def getObjectMapperComponent(def processInstance) {
        def mapperBeanName = processInstance.class.name
                .replace('dto', 'mapper')
                .replace('DTO', 'Mapper')
        return beanFactory.getBean(Class.forName(mapperBeanName))
    }
}
