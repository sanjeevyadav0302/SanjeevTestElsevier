package com.elsevier.external;

import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class GitHubServiceTest {


   @Mock
    private RestTemplate restTemplate;

   @InjectMocks
   private GitHubService gitHubService;

    @Test
    public void getAll_PublicRepositoriesForAGivenUser_FromGitHub() {
        List<RepositoryNames> repositoryNamesList = new ArrayList<>();
        RepositoryNames repositoryNames = new RepositoryNames();
        repositoryNames.setName("repo1");
        repositoryNamesList.add(repositoryNames);
        ResponseEntity<List<RepositoryNames>> myEntity = new ResponseEntity<List<RepositoryNames>>(repositoryNamesList,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("https://api.github.com/users/san/repos"),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.eq(null),
                ArgumentMatchers.<ParameterizedTypeReference<List<RepositoryNames>>>any())
        ).thenReturn(myEntity);
        List<RepositoryNames> res = gitHubService.getPublicRepositories( "san");
        assertEquals("repo1", res.get(0).getName());
    }


    @Test
    public void getAll_CollaboratorsForAGivenRepositories_FromGitHub() {
        List<Collaborators> collaborators = new ArrayList<>();
        Collaborators collaborator = new Collaborators();
        collaborator.setLogin("test1");
        collaborators.add(collaborator);
        ResponseEntity<List<Collaborators>> myEntity = new ResponseEntity<List<Collaborators>>(collaborators,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("https://api.github.com/repos/san/mars/collaborators"),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Collaborators>>>any())
        ).thenReturn(myEntity);
        List<Collaborators> res = gitHubService.getCollaborators("san","mars");
        assertEquals("test1", res.get(0).getLogin());
    }

    @Test
    public void getAll_ContributorsForAGivenRepositories_FromGitHub() {
        List<Contributors> contributors = new ArrayList<>();
        Contributors contributor = new Contributors();
        contributor.setLogin("test1");
        contributor.setContributions(10);
        contributors.add(contributor);
        ResponseEntity<List<Contributors>> myEntity = new ResponseEntity<List<Contributors>>(contributors,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("https://api.github.com/repos/san/mars/contributors"),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<List<Contributors>>>any())
        ).thenReturn(myEntity);
        List<Contributors> res = gitHubService.getContributors("san","mars");
        assertEquals("test1", res.get(0).getLogin());
        assertEquals(10, res.get(0).getContributions());
    }

}
