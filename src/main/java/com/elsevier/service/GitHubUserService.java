package com.elsevier.service;

import com.elsevier.external.GitHubService;
import com.elsevier.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubUserService {

    @Autowired
    private GitHubService gitHubService;

    @Value("${user.name}")
    private String userName;

    public List<UserRepositoryDetailsVO> getUserPublicRepositoriesDetail(String userName) {
        List<UserRepositoryDetailsVO> userRepositoryDetailsVOLst = new ArrayList<>();
        List<RepositoryNames> repositoryNames = gitHubService.getPublicRepositories(userName);
        repositoryNames.forEach(repositoryName -> {
            List<CollaboratorDetails> collaboratorDetails = new ArrayList<>();
            UserRepositoryDetailsVO userRepositoryDetailsVO = new UserRepositoryDetailsVO();
            userRepositoryDetailsVO.setRepositoryName(repositoryName.getName());
            List<Contributors> contributors = gitHubService.getContributors(userName, repositoryName.getName());
            List<Collaborators> collaborators = gitHubService.getCollaborators(userName, repositoryName.getName());
            List<Contributors> collaboratorsContribution = getCollaboratorsContribution(contributors, collaborators);
            List<CollaboratorDetails> collaboratorDetail = prepareCollaboratorsResponse(collaboratorDetails, collaboratorsContribution);
            userRepositoryDetailsVO.setCollaborators(collaboratorDetail);
            userRepositoryDetailsVOLst.add(userRepositoryDetailsVO);
        });
        return userRepositoryDetailsVOLst;
    }

    private List<Contributors> getCollaboratorsContribution(List<Contributors> contributors, List<Collaborators> collaborators) {
        List<Contributors> contributorsList = getContributorsWhoAreCollaborators(contributors, collaborators);
        return getDefaultContributionForCollaborators(collaborators, contributorsList);

    }


    private List<Contributors> getContributorsWhoAreCollaborators(List<Contributors> contributors, List<Collaborators> collaborators) {
        return contributors.stream().filter(contributor -> (collaborators.stream().map(Collaborators::getLogin).collect(Collectors.toList())).
                contains(contributor.getLogin())).collect(Collectors.toList());
    }

    private List<Contributors> getDefaultContributionForCollaborators(List<Collaborators> collaborators, List<Contributors> contributorsList) {
        collaborators.stream().map(Collaborators::getLogin).collect(Collectors.toList()).stream().
                filter(coll -> !(contributorsList.stream().map(Contributors::getLogin).collect(Collectors.toList())).contains(coll))
                .collect(Collectors.toList()).forEach(coll -> {
            Contributors contributor = new Contributors();
            contributor.setLogin(coll);
            contributorsList.add(contributor);
        });
        return contributorsList;
    }

    private List<CollaboratorDetails> prepareCollaboratorsResponse(List<CollaboratorDetails> collaboratorDetails, List<Contributors> collaboratorsContribution) {
        collaboratorsContribution.forEach(collaborator -> {
            CollaboratorDetails collaboratorDetail = new CollaboratorDetails();
            collaboratorDetail.setName(collaborator.getLogin());
            collaboratorDetail.setCommitsCount(collaborator.getContributions());
            collaboratorDetails.add(collaboratorDetail);
        });

        return collaboratorDetails;
    }
}