package com.elsevier.external;

import com.elsevier.exception.GitServiceException;
import com.elsevier.model.Collaborators;
import com.elsevier.model.Contributors;
import com.elsevier.model.RepositoryNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubService {

    @Value("${github.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;


    public List<RepositoryNames> getPublicRepositories(String userName) {

        String url = "https://api.github.com/users/" + userName + "/repos";
        ResponseEntity<List<RepositoryNames>> response;
        List<RepositoryNames> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RepositoryNames>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitServiceException("Exception occured while fetching all the repository from github ***" + e.getMessage());
        }
        return responseBody;
    }

    public List<Collaborators> getCollaborators(String owner, String repoName) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = "https://api.github.com/repos/" + owner + "/" + repoName + "/collaborators";
        ResponseEntity<List<Collaborators>> response;
        List<Collaborators> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(url,
                    HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Collaborators>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitServiceException("Exception occured while fetching all the collobrators from github ***" + e.getMessage());
        }
        return responseBody;
    }

    public List<Contributors> getContributors(String owner, String repoName) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = "https://api.github.com/repos/" + owner + "/" + repoName + "/contributors";
        ResponseEntity<List<Contributors>> response;
        List<Contributors> responseBody = new ArrayList<>();
        try {
            response = restTemplate.exchange(url,
                    HttpMethod.GET, entity,
                    new ParameterizedTypeReference<List<Contributors>>() {
                    });
            responseBody = response.getBody();
        } catch (Exception e) {
            throw new GitServiceException("Exception occured while fetching all the contributors from github ***" + e.getMessage());
        }
        return responseBody;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "token " + token);
        return headers;
    }


}
