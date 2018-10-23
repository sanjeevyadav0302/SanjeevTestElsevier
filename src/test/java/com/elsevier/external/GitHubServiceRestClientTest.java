package com.elsevier.external;

import com.elsevier.exception.GitHubServiceException;
import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;
import com.elsevier.util.GitHubDataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class GitHubServiceRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GitHubServiceRestClient gitHubServiceRestClient;

    private GitHubDataUtil gitHubDataUtil = new GitHubDataUtil();

    @Test
    public void getPublicRepositories_ForAValidUser_ShouldGivePublicRepositoriesInfo() {
        List<RepositoryNames> repositoryNames = gitHubDataUtil.getRepositoryNames();
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNames, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenReturn(myEntity);
        List<RepositoryNames> res = gitHubServiceRestClient.getPublicRepositories("san");
        assertEquals("repo1", res.get(0).getName());
        assertEquals("repo2", res.get(1).getName());
    }

    @Test(expected = GitHubServiceException.class)
    public void getPublicRepositories_ForANonUser_ShouldThrowServiceException() {
        List<RepositoryNames> repositoryNames = gitHubDataUtil.getRepositoryNames();
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNames, HttpStatus.OK);
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenThrow(new GitHubServiceException("git hub service failed"));
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
        List<Collaborators> collaborators = gitHubDataUtil.getCollaborators();
        ResponseEntity<List<Collaborators>> myEntity = new ResponseEntity<List<Collaborators>>(collaborators, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Collaborators>>>any())
        ).thenReturn(myEntity);
        List<Collaborators> res = gitHubServiceRestClient.getCollaborators("san", "mars");
        assertEquals("test1", res.get(0).getLogin());
        assertEquals("test2", res.get(1).getLogin());
    }

    @Test
    public void getContributorsForAValidUser_ShouldGiveContributorsInfo() {
        List<Contributors> contributors = gitHubDataUtil.getContributors();
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
        assertEquals("test2", res.get(1).getLogin());
        assertEquals(8, res.get(1).getContributions());
        assertEquals("test3", res.get(2).getLogin());
        assertEquals(7, res.get(2).getContributions());
    }

    @Test
    public void getContributors_ForAValidUser_ShouldGiveNoContributonWhenNoContributorsHasCommited() {
        List<Contributors> contributors = new ArrayList<>();
        ResponseEntity<List<Contributors>> myEntity = new ResponseEntity<List<Contributors>>(contributors, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Contributors>>>any())
        ).thenReturn(myEntity);
        List<Contributors> res = gitHubServiceRestClient.getContributors("san", "mars");
        assertEquals(0, res.size());

    }

}
