package com.elsevier.service;

import com.elsevier.external.GitHubServiceRestClient;
import com.elsevier.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPublicRepositoryInfoService {

    @Autowired
    private GitHubServiceRestClient gitHubServiceRestClient;


    /**
     * This method will return repositories info with collaborators details
     *
     * @param userName
     * @return List<UserRepositoriesInfo>
     */
    public List<UserRepositoriesInfo> getPublicRepositoriesInfo(String userName) {
        List<UserRepositoriesInfo> userRepositoriesInfo = new ArrayList<>();
        List<RepositoryNames> repositoryNames = gitHubServiceRestClient.getPublicRepositories(userName);
        repositoryNames.forEach(repositoryName -> {
            List<CollaboratorDetails> collaboratorDetails = new ArrayList<>();
            UserRepositoriesInfo userRepositoryInfo = new UserRepositoriesInfo();
            userRepositoryInfo.setRepositoryName(repositoryName.getName());
            List<Contributors> contributors = gitHubServiceRestClient.getContributors(userName, repositoryName.getName());
            List<Collaborators> collaborators = gitHubServiceRestClient.getCollaborators(userName, repositoryName.getName());
            List<Contributors> collaboratorsContribution = getCollaboratorsContribution(contributors, collaborators);
            List<CollaboratorDetails> collaboratorDetail = prepareCollaboratorsResponse(collaboratorDetails, collaboratorsContribution);
            userRepositoryInfo.setCollaborators(collaboratorDetail);
            userRepositoriesInfo.add(userRepositoryInfo);
        });
        return userRepositoriesInfo;
    }

    /** This method will return list of collaborators with their contribution
     * @param contributors
     * @param collaborators
     * @return List<Contributors>
     */
    private List<Contributors> getCollaboratorsContribution(List<Contributors> contributors, List<Collaborators> collaborators) {
        List<Contributors> contributorsList = getContributorsWhoAreCollaborators(contributors, collaborators);
        return getDefaultContributionForCollaborators(collaborators, contributorsList);

    }

    /** This will return contributors who are collaborators
     * @param contributors
     * @param collaborators
     * @return List<Contributors>
     */
    private List<Contributors> getContributorsWhoAreCollaborators(List<Contributors> contributors, List<Collaborators> collaborators) {
        return contributors.stream().filter(contributor -> (collaborators.stream().map(Collaborators::getLogin).collect(Collectors.toList())).
                contains(contributor.getLogin())).collect(Collectors.toList());
    }

    /** This method will return Collaborators default contribution o as they are not contributors.
     * @param collaborators
     * @param contributorsList
     * @return List<Contributors>
     */
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

    /** prepare the final collaborators details with contribution
     * @param collaboratorDetails
     * @param collaboratorsContribution
     * @return List<CollaboratorDetails>
     */
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