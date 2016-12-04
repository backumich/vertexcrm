package ua.com.vertex.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.vertex.context.TestMainContext;
import ua.com.vertex.dao.interfaces.CertificateDaoInf;
import ua.com.vertex.dao.interfaces.UserDaoInf;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestMainContext.class)
@ActiveProfiles("testExternalDb")
public class CertDetailsPageLogicImplTestExtDB {

    @Autowired
    private UserDaoInf userDao;

    @Autowired
    private CertificateDaoInf certificateDao;

    @Test()
    public void getUserPhotoForExistingUserWithPhotoShouldReturnNonZeroLengthArray() throws IOException {
        CertDetailsPageLogicImpl logic = new CertDetailsPageLogicImpl(userDao, certificateDao);
        String userId = "14";
        assertEquals(logic.getUserPhoto(userId).length, 631);
    }
}
