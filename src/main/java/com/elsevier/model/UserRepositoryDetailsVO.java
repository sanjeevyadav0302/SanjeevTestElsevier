package com.elsevier.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class UserRepositoryDetailsVO {

    private String repositoryName;

    private List<CollaboratorDetails> collaborators = new ArrayList<>();

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public List<CollaboratorDetails> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<CollaboratorDetails> collaborators) {
        this.collaborators = collaborators;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,JSON_STYLE);
    }
}
