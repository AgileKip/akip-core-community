package org.akip.dashboard.builder;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.util.List;
import java.util.Map;

import org.akip.dashboard.model.BoxGroupModel;
import org.akip.dashboard.model.BoxModel;
import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.model.GroupModel;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.groovy.BindingBuilder;
import org.springframework.stereotype.Service;

@Service
public class AkipDashboardCustomBoxGroupBuilder extends DashboardGroupBuilder {

    private final BindingBuilder bindingBuilder;

    public AkipDashboardCustomBoxGroupBuilder(BindingBuilder bindingBuilder) {
        this.bindingBuilder = bindingBuilder;
    }

    @Override
    public GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig) {
        BoxGroupModel boxGroupModel = new BoxGroupModel().title(groupConfig.getTitle());
        Binding binding = bindingBuilder.buildBinding();
        binding.setProperty("dashboardRequest", dashboardRequest);
        GroovyShell shell = new GroovyShell(binding);
        List<Map<Object, Object>> boxesAsListOfMaps = (List<Map<Object, Object>>) shell.evaluate(groupConfig.getExpression());
        boxesAsListOfMaps.forEach(
            map -> {
                boxGroupModel.addBox(new BoxModel().title((String) map.get("title")).value(String.valueOf(map.get("value"))));
            }
        );
        return boxGroupModel;
    }
}
