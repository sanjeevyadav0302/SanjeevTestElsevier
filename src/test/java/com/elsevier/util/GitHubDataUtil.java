package com.elsevier.util;

import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;

import java.util.ArrayList;
import java.util.List;

public class GitHubDataUtil {

    public List<RepositoryNames> getRepositoryNames() {
        List<RepositoryNames> repositoryNames = new ArrayList<>();
        RepositoryNames repositoryName1= new RepositoryNames();
        repositoryName1.setFull_name("repo1");
        repositoryName1.setName("repo1");
        repositoryNames.add(repositoryName1);
        RepositoryNames repositoryName2 = new RepositoryNames();
        repositoryName2.setFull_name("repo2");
        repositoryName2.setName("repo2");
        repositoryNames.add(repositoryName2);
        return repositoryNames;
    }

    public  List<Contributors> getContributors(){
        List<Contributors> contributors = new ArrayList<>();
        Contributors contributor1 = new Contributors();
        contributor1.setLogin("test1");
        contributor1.setContributions(10);
        contributors.add(contributor1);
        Contributors contributor2 = new Contributors();
        contributor2.setLogin("test2");
        contributor2.setContributions(8);
        contributors.add(contributor2);
        Contributors contributor3 = new Contributors();
        contributor3.setLogin("test3");
        contributor3.setContributions(7);
        contributors.add(contributor3);
        return contributors;
    }

    public List<Collaborators> getCollaborators(){
        List<Collaborators> collaborators = new ArrayList<>();
        Collaborators collaborators1 = new Collaborators();
        collaborators1.setLogin("test1");
        collaborators.add(collaborators1);
        Collaborators collaborators2 = new Collaborators();
        collaborators2.setLogin("test2");
        collaborators.add(collaborators2);
        return collaborators;
    }

    public List<Collaborators> getCollaboratorsWithNoMatchingContributors(){
        List<Collaborators> collaborators = new ArrayList<>();
        Collaborators collaborators1 = new Collaborators();
        collaborators1.setLogin("san");
        collaborators.add(collaborators1);
        Collaborators collaborators2 = new Collaborators();
        collaborators2.setLogin("jee");
        collaborators.add(collaborators2);
        return collaborators;
    }

    public List<Collaborators> getCollaboratorsWithOneNonMatchingContributors(){
        List<Collaborators> collaborators = new ArrayList<>();
        Collaborators collaborators1 = new Collaborators();
        collaborators1.setLogin("test1");
        collaborators.add(collaborators1);
        Collaborators collaborators2 = new Collaborators();
        collaborators2.setLogin("jee");
        collaborators.add(collaborators2);
        return collaborators;
    }
}
