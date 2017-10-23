package ua.com.vertex.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.com.vertex.context.TestConfig;
import ua.com.vertex.dao.interfaces.UserDaoInf;
import ua.com.vertex.logic.interfaces.UserLogic;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class UserLogicImplTestWithContext {

    @Autowired
    private UserDaoInf dao;

    @Autowired
    private UserLogic logic;

    @Test
    public void getEmailByUuidReturnsEmailIfNotExpired() {
        final String email = "email";
        final String uuid = UUID.randomUUID().toString();
        final int minutes = 5;
        long id = createNewRecordInDB(email, minutes, uuid);

        String result = logic.getEmailByUuid(id, uuid);
        assertEquals(email, result);
    }

    private long createNewRecordInDB(String email, int minutes, String uuid) {
        return dao.setParamsToRestorePassword(email, uuid, LocalDateTime.now().minusMinutes(minutes));
    }

    @Test
    public void getEmailByUuidReturnsEmptyStringIfExpired() {
        final String email = "email";
        final String uuid = UUID.randomUUID().toString();
        final int minutes = 60;
        long id = createNewRecordInDB(email, minutes, uuid);

        String result = logic.getEmailByUuid(id, uuid);
        assertEquals("", result);
    }
}
