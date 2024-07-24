package org.akip.form.camundaForm7;

import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class AkipAttachmentsField extends AbstractFormFieldType {

    @Override
    public String getName() {
        return AkipAttachmentsField.class.getSimpleName();
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
