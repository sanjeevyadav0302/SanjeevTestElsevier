package com.elsevier;

import com.elsevier.exception.UserNameNotFound;
import com.elsevier.model.UserRepositoriesInfo;
import com.elsevier.service.UserPublicRepositoryInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SanjeevTestElsevierApplication.class);

    @Autowired
    UserPublicRepositoryInfoService userPublicRepositoryInfoService;

    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        SpringApplication.run(SanjeevTestElsevierApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        boolean containsOption = args.containsOption("user.name");
        if (!containsOption) {
            throw new UserNameNotFound("Please pass user name as command line parameter");
        }
        List<UserRepositoriesInfo> userRepositoryDetail = userPublicRepositoryInfoService.getPublicRepositoriesInfo(userName);
        log.info("response is : {}" + userRepositoryDetail);
    }


}
