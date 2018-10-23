package com.elsevier;

import com.elsevier.exception.UserNameNotFound;
import com.elsevier.model.UserRepositoriesInfo;
import com.elsevier.service.UserPublicRepositoryInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.ApplicationArguments;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class SanjeevTestElsevierApplicationTest {

    @Mock
    UserPublicRepositoryInfoService userPublicRepositoryInfoService;
    @Mock
    private ApplicationArguments args;
    @InjectMocks
    private SanjeevTestElsevierApplication app = new SanjeevTestElsevierApplication();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = UserNameNotFound.class)
    public void applicationShouldThrow_UserNotFound_WhenNoUserNamePassedViaCommandLine() throws Exception {
        when(args.getSourceArgs()).thenReturn(new String[]{""});
        app.run(args);
    }

    @Test
    public void applicationShouldStartSuccessFully_WhenUserNameFoundViaCommandLine() throws Exception {
        List<UserRepositoriesInfo> lst = new ArrayList<>();
        UserRepositoriesInfo userRepositoriesInfo = new UserRepositoriesInfo();
        userRepositoriesInfo.setRepositoryName("repo1");
        lst.add(userRepositoriesInfo);
        when(args.getSourceArgs()).thenReturn(new String[]{"san"});
        when(args.containsOption("user.name")).thenReturn(true);
        when(userPublicRepositoryInfoService.getPublicRepositoriesInfo("san")).thenReturn(lst);
        app.run(args);
    }


}
