package com.elsevier.service;

import com.elsevier.external.GitHubServiceRestClient;
import com.elsevier.model.UserRepositoriesInfo;
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
public class UserPublicRepositoryInfoServiceTest {

    @InjectMocks
    private UserPublicRepositoryInfoService userPublicRepositoryInfoService;
    @Mock
    private GitHubServiceRestClient gitHubServiceRestClient;
    private GitHubDataUtil gitHubDataUtil = new GitHubDataUtil();

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPublicRepositoriesInfo_WhenCollaboratorsAreContributors_ShouldBeSuccessful() {
        when(gitHubServiceRestClient.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubServiceRestClient.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaborators());

        when(gitHubServiceRestClient.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoriesInfo> userRepoDetails = userPublicRepositoryInfoService.getPublicRepositoriesInfo("san");
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
    public void getPublicRepositoriesInfo_WhenCollaboratorsAreNotContributors_ShouldHaveCommitCountZero() {
        when(gitHubServiceRestClient.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubServiceRestClient.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaboratorsWithNoMatchingContributors());

        when(gitHubServiceRestClient.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoriesInfo> userRepoDetails = userPublicRepositoryInfoService.getPublicRepositoriesInfo("san");
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
    public void getPublicRepositoriesInfo_WhenOneCollaboratorsIsNotContributors_ShouldBeSuccessFull() {
        when(gitHubServiceRestClient.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubServiceRestClient.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getCollaboratorsWithOneNonMatchingContributors());

        when(gitHubServiceRestClient.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getContributors());

        List<UserRepositoriesInfo> userRepoDetails = userPublicRepositoryInfoService.getPublicRepositoriesInfo("san");
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

    @Test
    public void getPublicRepositoriesInfo_ShouldOnlyGiveOnlyOwnerAsCollaborators_WhenThereIsNoOtherCollaborators() {
        when(gitHubServiceRestClient.getPublicRepositories(anyString()))
                .thenReturn(gitHubDataUtil.getRepositoryNames());

        when(gitHubServiceRestClient.getCollaborators(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getOwnerAsCollaborator());

        when(gitHubServiceRestClient.getContributors(anyString(), anyString()))
                .thenReturn(gitHubDataUtil.getOwnerAsContributors());

        List<UserRepositoriesInfo> userRepoDetails = userPublicRepositoryInfoService.getPublicRepositoriesInfo("san");
        assertNotNull(userRepoDetails);
        assertEquals(2, userRepoDetails.size());
        assertEquals(1, userRepoDetails.get(0).getCollaborators().size());
        assertEquals(1, userRepoDetails.get(1).getCollaborators().size());
        assertEquals("repo1", userRepoDetails.get(0).getRepositoryName());
        assertEquals("repo2", userRepoDetails.get(1).getRepositoryName());
        assertEquals("owner1", userRepoDetails.get(0).getCollaborators().get(0).getName());
        assertEquals(2, userRepoDetails.get(0).getCollaborators().get(0).getCommitsCount());
        assertEquals("owner1", userRepoDetails.get(1).getCollaborators().get(0).getName());
        assertEquals(2, userRepoDetails.get(1).getCollaborators().get(0).getCommitsCount());
    }

}
