package com.elsevier.external;

import com.elsevier.exception.GitServiceException;
import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
/*@TestPropertySource(properties = {
        "github.host=null",
})*/
@PropertySource("classpath:application.properties")
public class GitHubServiceRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Value("${github.host}")
    private String host;

    @InjectMocks
    private GitHubServiceRestClient gitHubServiceRestClient;

    @Test
    public void getPublicRepositories_ForAValidUser_ShouldGivePublicRepositoriesInfo() {
        List<RepositoryNames> repositoryNamesList = new ArrayList<>();
        RepositoryNames repositoryNames = new RepositoryNames();
        repositoryNames.setName("repo1");
        repositoryNamesList.add(repositoryNames);
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNamesList, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenReturn(myEntity);
        List<RepositoryNames> res = gitHubServiceRestClient.getPublicRepositories("san");
        assertEquals("repo1", res.get(0).getName());
    }

    @Test(expected = GitServiceException.class)
    public void getPublicRepositories_ForANonUser_ShouldThroughServiceException() {
        List<RepositoryNames> repositoryNamesList = new ArrayList<>();
        RepositoryNames repositoryNames = new RepositoryNames();
        repositoryNames.setName("repo1");
        repositoryNamesList.add(repositoryNames);
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNamesList, HttpStatus.OK);
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenThrow(new GitServiceException("message"));
        List<RepositoryNames> res = gitHubServiceRestClient.getPublicRepositories("s23456");
    }

    @Test
    public void getPublicRepositories_ForAValidUser_ShouldGiveNoRepositoriesInfoIfNoRepoIsPublic() {
        List<RepositoryNames> repositoryNames = new ArrayList<>();
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNames, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenReturn(myEntity);
        List<RepositoryNames> res = gitHubServiceRestClient.getPublicRepositories("s23456");
        assertEquals(0, res.size());
    }

    @Test
    public void getCollaborators_ForAValidUser_ShouldGiveCollaboratorsInfo() {
        List<Collaborators> collaborators = new ArrayList<>();
        Collaborators collaborator = new Collaborators();
        collaborator.setLogin("test1");
        collaborators.add(collaborator);
        ResponseEntity<List<Collaborators>> myEntity = new ResponseEntity<List<Collaborators>>(collaborators, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Collaborators>>>any())
        ).thenReturn(myEntity);
        List<Collaborators> res = gitHubServiceRestClient.getCollaborators("san", "mars");
        assertEquals("test1", res.get(0).getLogin());
    }

    @Test
    public void getContributors_ForAValidUser_ShouldGiveContributorsInfo() {
        List<Contributors> contributors = new ArrayList<>();
        Contributors contributor = new Contributors();
        contributor.setLogin("test1");
        contributor.setContributions(10);
        contributors.add(contributor);
        ResponseEntity<List<Contributors>> myEntity = new ResponseEntity<List<Contributors>>(contributors, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Contributors>>>any())
        ).thenReturn(myEntity);
        List<Contributors> res = gitHubServiceRestClient.getContributors("san", "mars");
        assertEquals("test1", res.get(0).getLogin());
        assertEquals(10, res.get(0).getContributions());
    }

}
