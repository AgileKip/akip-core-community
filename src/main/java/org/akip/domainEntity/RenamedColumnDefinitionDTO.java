package org.akip.domainEntity;

public class RenamedColumnDefinitionDTO {

    String originalColumnName;
    String newColumnName;

    public String getOriginalColumnName() {
        return originalColumnName;
    }

    public void setOriginalColumnName(String originalColumnName) {
        this.originalColumnName = originalColumnName;
    }

    public String getNewColumnName() {
        return newColumnName;
    }

    public void setNewColumnName(String newColumnName) {
        this.newColumnName = newColumnName;
    }

    public RenamedColumnDefinitionDTO(String originalColumnName, String newColumnName) {
        this.originalColumnName = originalColumnName;
        this.newColumnName = newColumnName;
    }



}
