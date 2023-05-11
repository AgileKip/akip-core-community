package org.akip.groovy

import org.akip.camunda.CamundaConstants;

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

import javax.persistence.EntityManager

@Component
class BindingBuilder {

    @Value('${akip.projectBasePackage}')
    private String projectBasePackage;

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
        def processEntity = Class.forName("${projectBasePackage}.service.dto.${processEntityName}DTO")
                .getDeclaredConstructor()
                .newInstance()
        processEntity.id = processEntityId
        return buildBindingFromProcessEntity(processEntity)
    }


    def loadObjectFromDB(def processInstance) {
        if (!processInstance.id) {
           return processInstance
        }

        return getObjectServiceComponent(processInstance)
                .findOne(processInstance.id)
                .get()

//        return getObjectMapperComponent(processInstance)
//            .toDto(getObjectRepositoryComponent(processInstance).findById(processInstance.id).get())
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

    // Originally I used the methods above (that use beanFactory.getBean(Class.forName(xpto)))
    // but I don't know why it suddenly starts to throw the exception 'No qualify bean of type...'
    // I replaced for the 'getBean' that receives the simple bean name and it works again.
    def getObjectServiceComponent(def processInstance) {
        def serviceBeanName = processInstance.class.simpleName.uncapitalize()
                //.replace('.dto', '')
                .replace('DTO', 'Service')
        return beanFactory.getBean(serviceBeanName)
    }
}
