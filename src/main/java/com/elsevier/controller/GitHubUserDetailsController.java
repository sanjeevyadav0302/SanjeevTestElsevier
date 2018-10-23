package com.elsevier.controller;

import com.elsevier.model.UserRepositoriesInfo;
import com.elsevier.service.UserPublicRepositoryInfoService;
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
    UserPublicRepositoryInfoService userPublicRepositoryInfoService;
    @RequestMapping(value = "git", method = RequestMethod.GET)
    public List<UserRepositoriesInfo> gitHubUserDetails() {
        return userPublicRepositoryInfoService.getPublicRepositoriesInfo(userName);
    }
}
