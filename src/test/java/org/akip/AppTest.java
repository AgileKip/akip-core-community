package org.akip;

import static org.junit.Assert.*;

import org.akip.camunda.form.CamundaFormFieldDef;
import org.akip.service.ProcessDefinitionService;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaFormData;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test() {
        File file = new File("/Users/utelemaco/development/workspaceJHipster7/bpmns/RoadmapDev.bpmn");
        BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);

        ModelElementType startEventType = modelInstance.getModel().getType(StartEvent.class);
        StartEvent startEvent = (StartEvent) modelInstance.getModelElementsByType(startEventType).iterator().next();

        if (!startEvent.getExtensionElements().getElements().isEmpty()) {
            CamundaFormData camundaFormData = (CamundaFormData) startEvent.getExtensionElements().getElements().stream().collect(Collectors.toList()).get(0);
            List fields = camundaFormData.getCamundaFormFields()
                    .stream()
//                    .map(this::toCamundaFormFieldDef)
                    .collect(Collectors.toList());
            System.out.println(fields);
        }

        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        ProcessDefinitionService service = new ProcessDefinitionService(null, null);
        List<CamundaFormFieldDef> extractFormFields = service.extractFormFields(modelInstance);

        System.out.println(extractFormFields);
        System.out.println(startEvent);

        System.out.println(process);

        //List events = process.getEventDefinitions().stream().collect(Collectors.toList());
        //System.out.println(events);
        //assertNotNull(events);
        assertNotNull(modelInstance);
    }
}
