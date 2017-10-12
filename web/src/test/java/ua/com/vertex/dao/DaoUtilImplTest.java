package ua.com.vertex.dao;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ua.com.vertex.dao.interfaces.DaoUtilInf;
import ua.com.vertex.utils.DataNavigator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DaoUtilImplTest {

    @Test
    public void getPagingSQLParametersTest() throws Exception {
        String MSG = "Maybe calculation of paging parameters was changed";
        DaoUtilInf daoUtil = new DaoUtilImpl();
        DataNavigator dataNavigator = new DataNavigator("", 3, 10, 40);
        MapSqlParameterSource parameters = daoUtil.getPagingSQLParameters(dataNavigator);

        assertTrue(parameters.hasValue("from"));
        assertTrue(parameters.hasValue("offset"));

        assertEquals(MSG, 20, parameters.getValue("from"));
        assertEquals(MSG, 10, parameters.getValue("offset"));
    }

}
