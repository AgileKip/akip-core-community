package org.akip.domainEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDomainEntityDescriptorDTO {

    private Boolean updateAllColumns = Boolean.TRUE;
    private List<String> readOnlyColumns = new ArrayList<>();
    private List<String> updatedColumns = new ArrayList<>();
    private Boolean updateAllHasManyRelations = Boolean.TRUE;
    private List<String> readOnlyHasManyRelations = new ArrayList<>();
    private List<String> updatedHasManyRelations = new ArrayList<>();
    private Map<String, UpdateDomainEntityDescriptorDTO> updateDomainEntityDescriptorForHasManyRelations = new HashMap<>();


    public static UpdateDomainEntityDescriptorDTO defaultUpdateDomainEntityDescriptorDTO() {
        return new UpdateDomainEntityDescriptorDTO().addReadOnlyColumn("id");
    }

    public Boolean getUpdateAllColumns() {
        return updateAllColumns;
    }

    public void setUpdateAllColumns(Boolean updateAllColumns) {
        this.updateAllColumns = updateAllColumns;
    }


    public UpdateDomainEntityDescriptorDTO updateAllColumns(Boolean updateAllColumns) {
        this.updateAllColumns = updateAllColumns;
        return this;
    }

    public List<String> getReadOnlyColumns() {
        return readOnlyColumns;
    }

    public UpdateDomainEntityDescriptorDTO addReadOnlyColumn(String columnName) {
        readOnlyColumns.add(columnName);
        return this;
    }

    public List<String> getUpdatedColumns() {
        return updatedColumns;
    }

    public UpdateDomainEntityDescriptorDTO addUpdatedColumn(String columnName) {
        updatedColumns.add(columnName);
        return this;
    }

    public Boolean getUpdateAllHasManyRelations() {
        return updateAllHasManyRelations;
    }

    public void setUpdateAllHasManyRelations(Boolean updateAllHasManyRelations) {
        this.updateAllHasManyRelations = updateAllHasManyRelations;
    }

    public UpdateDomainEntityDescriptorDTO updateAllHasManyRelations(Boolean updateAllHasManyRelations) {
        this.updateAllHasManyRelations = updateAllHasManyRelations;
        return this;
    }

    public List<String> getReadOnlyHasManyRelations() {
        return readOnlyHasManyRelations;
    }

    public UpdateDomainEntityDescriptorDTO addReadOnlyHasManyRelation(String relationName) {
        readOnlyHasManyRelations.add(relationName);
        return this;
    }

    public List<String> getUpdatedHasManyRelations() {
        return updatedHasManyRelations;
    }

    public UpdateDomainEntityDescriptorDTO addUpdatedHasManyRelation(String relationName) {
        updatedHasManyRelations.add(relationName);
        return this;
    }

    public Map<String, UpdateDomainEntityDescriptorDTO> getUpdateDomainEntityDescriptorForHasManyRelations() {
        return updateDomainEntityDescriptorForHasManyRelations;
    }

    public UpdateDomainEntityDescriptorDTO getUpdateDomainEntityDescriptorForHasManyRelation(String relationName) {
        UpdateDomainEntityDescriptorDTO updateDomainEntityDescriptor = updateDomainEntityDescriptorForHasManyRelations.get(relationName);
        return updateDomainEntityDescriptor != null ? updateDomainEntityDescriptor : UpdateDomainEntityDescriptorDTO.defaultUpdateDomainEntityDescriptorDTO();
    }

    public UpdateDomainEntityDescriptorDTO addUpdateDomainEntityDescriptorForHasManyRelation(String relationName, UpdateDomainEntityDescriptorDTO updateDomainEntityDescriptor) {
        updateDomainEntityDescriptorForHasManyRelations.put(relationName, updateDomainEntityDescriptor);
        return this;
    }
}
