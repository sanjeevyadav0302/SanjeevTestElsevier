package com.elsevier.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class CollaboratorDetails {

    private String name;
    private int commitsCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommitsCount() {
        return commitsCount;
    }

    public void setCommitsCount(int commitsCount) {
        this.commitsCount = commitsCount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,JSON_STYLE);
    }
}
