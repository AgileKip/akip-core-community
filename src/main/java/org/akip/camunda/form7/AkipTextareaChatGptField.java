package org.akip.camunda.form7;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class AkipTextareaChatGptField extends AbstractFormFieldType {

    @Override
    public String getName() {
        return AkipTextareaChatGptField.class.getSimpleName();
    }

    @Override
    public TypedValue convertToFormValue(TypedValue typedValue) {
        return null;
    }

    @Override
    public TypedValue convertToModelValue(TypedValue typedValue) {
        return null;
    }

    @Override
    public Object convertFormValueToModelValue(Object o) {
        return null;
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return null;
    }
}
