package com.elsevier;

import com.elsevier.exception.UserNameNotFound;
import com.elsevier.model.UserRepositoryDetailsVO;
import com.elsevier.service.GitHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SanjeevTestElsevierApplication implements ApplicationRunner {

    @Autowired
    GitHubUserService gitHubUserService;

    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        SpringApplication.run(SanjeevTestElsevierApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Application started with command-line arguments: {}" + Arrays.toString(args.getSourceArgs()));
        boolean containsOption = args.containsOption("user.name");
        if (!containsOption) {
            throw new UserNameNotFound("Please pass user name as command line parameter");
        }
        List<UserRepositoryDetailsVO> userRepositoryDetailsVOList = gitHubUserService.getUserPublicRepositoriesDetail(userName);
        System.out.println("" + userRepositoryDetailsVOList);
    }


}
