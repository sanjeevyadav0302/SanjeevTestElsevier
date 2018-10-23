package com.elsevier.external;

import com.elsevier.exception.GitHubServiceException;
import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;
import com.elsevier.util.UriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubServiceRestClient {

    @Value("${github.OAuth.token}")
    private String token;

    @Value("${github.host}")
    private String host;


    @Autowired
    private RestTemplate restTemplate;

    /**
     * get all public repositories for a user
     *
     * @param userName
     * @return
     */
    public List<RepositoryNames> getPublicRepositories(String userName) {
        ResponseEntity<List<RepositoryNames>> response;
        List<RepositoryNames> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(host + UriUtil.getRepositoryUri(userName),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RepositoryNames>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitHubServiceException("Exception occured while fetching public repository from github ***" + e.getMessage());
        }
        return responseBody;
    }

    /**
     * get all the collaborators for a given owner/user name and repositories
     *
     * @param owner
     * @param repoName
     * @return
     */
    public List<Collaborators> getCollaborators(String owner, String repoName) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<List<Collaborators>> response;
        List<Collaborators> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(host + UriUtil.getCollaboratorUri(owner, repoName),
                    HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Collaborators>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitHubServiceException("Exception occured while fetching collobrators from github ***" + e.getMessage());
        }
        return responseBody;
    }

    /**
     * get all the contributors for a given owner/user and repo name
     *
     * @param owner
     * @param repoName
     * @return
     */

    public List<Contributors> getContributors(String owner, String repoName) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<List<Contributors>> response;
        List<Contributors> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(host + UriUtil.getContributorsUri(owner, repoName),
                    HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Contributors>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitHubServiceException("Exception occured while fetching contributors from github ***" + e.getMessage());
        }
        return responseBody;
    }

    /**
     * create headers to set OAuth token for gitHub
     *
     * @return
     */
    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "token " + token);
        return headers;
    }
}
