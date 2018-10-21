package com.elsevier.service;

import com.elsevier.external.GitHubService;
import com.elsevier.model.UserRepositoryDetailsVO;
import com.elsevier.util.GitHubDataUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GitHubUserServiceTest {

    @InjectMocks
    private GitHubUserService gitHubUserService;

    @Mock
    private GitHubService gitHubService;
    private GitHubDataUtil gitHubDataUtil = new GitHubDataUtil();

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userPublicRepositoriesDetails_WhenCollaboratorsAreContributors() {
        when(gitHubService.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubService.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaborators());

        when(gitHubService.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoryDetailsVO> userRepoDetails = gitHubUserService.getUserPublicRepositoriesDetail("san");
        assertNotNull(userRepoDetails);
        assertEquals("repo1", userRepoDetails.get(0).getRepositoryName());
        assertEquals("repo2", userRepoDetails.get(1).getRepositoryName());
        assertEquals("test1", userRepoDetails.get(0).getCollaborators().get(0).getName());
        assertEquals(10, userRepoDetails.get(0).getCollaborators().get(0).getCommitsCount());
        assertEquals("test2", userRepoDetails.get(0).getCollaborators().get(1).getName());
        assertEquals(8, userRepoDetails.get(0).getCollaborators().get(1).getCommitsCount());
        assertEquals("test1", userRepoDetails.get(1).getCollaborators().get(0).getName());
        assertEquals(10, userRepoDetails.get(1).getCollaborators().get(0).getCommitsCount());
        assertEquals("test2", userRepoDetails.get(1).getCollaborators().get(1).getName());
        assertEquals(8, userRepoDetails.get(1).getCollaborators().get(1).getCommitsCount());
    }


    @Test
    public void userPublicRepositoriesDetails_WhenCollaborators_AreNotContributors() {
        when(gitHubService.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubService.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaboratorsWithNoMatchingContributors());

        when(gitHubService.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoryDetailsVO> userRepoDetails = gitHubUserService.getUserPublicRepositoriesDetail("san");
        assertNotNull(userRepoDetails);
        assertEquals("repo1", userRepoDetails.get(0).getRepositoryName());
        assertEquals("repo2", userRepoDetails.get(1).getRepositoryName());
        assertEquals("san", userRepoDetails.get(0).getCollaborators().get(0).getName());
        assertEquals(0, userRepoDetails.get(0).getCollaborators().get(0).getCommitsCount());
        assertEquals("jee", userRepoDetails.get(0).getCollaborators().get(1).getName());
        assertEquals(0, userRepoDetails.get(0).getCollaborators().get(1).getCommitsCount());
        assertEquals("san", userRepoDetails.get(1).getCollaborators().get(0).getName());
        assertEquals(0, userRepoDetails.get(1).getCollaborators().get(0).getCommitsCount());
        assertEquals("jee", userRepoDetails.get(1).getCollaborators().get(1).getName());
        assertEquals(0, userRepoDetails.get(1).getCollaborators().get(1).getCommitsCount());
    }

    @Test
    public void userPublicRepositoriesDetails_WhenOneCollaborators_IsNotContributors() {
        when(gitHubService.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubService.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaboratorsWithOneNonMatchingContributors());

        when(gitHubService.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoryDetailsVO> userRepoDetails = gitHubUserService.getUserPublicRepositoriesDetail("san");
        assertNotNull(userRepoDetails);
        assertEquals("repo1", userRepoDetails.get(0).getRepositoryName());
        assertEquals("repo2", userRepoDetails.get(1).getRepositoryName());
        assertEquals("test1", userRepoDetails.get(0).getCollaborators().get(0).getName());
        assertEquals(10, userRepoDetails.get(0).getCollaborators().get(0).getCommitsCount());
        assertEquals("jee", userRepoDetails.get(0).getCollaborators().get(1).getName());
        assertEquals(0, userRepoDetails.get(0).getCollaborators().get(1).getCommitsCount());
        assertEquals("test1", userRepoDetails.get(1).getCollaborators().get(0).getName());
        assertEquals(10, userRepoDetails.get(1).getCollaborators().get(0).getCommitsCount());
        assertEquals("jee", userRepoDetails.get(1).getCollaborators().get(1).getName());
        assertEquals(0, userRepoDetails.get(1).getCollaborators().get(1).getCommitsCount());
    }


}
