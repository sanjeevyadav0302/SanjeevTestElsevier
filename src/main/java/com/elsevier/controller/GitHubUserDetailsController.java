package com.elsevier.controller;

import com.elsevier.model.UserRepositoryDetailsVO;
import com.elsevier.service.GitHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GitHubUserDetailsController {

    @Value("${user.name}")
    private String userName;

    @Autowired
    GitHubUserService gitHubUserService;
    @RequestMapping(value = "git", method = RequestMethod.GET)
    public List<UserRepositoryDetailsVO> gitHubUserDetails() {
        return gitHubUserService.getUserPublicRepositoriesDetail(userName);
    }
}
