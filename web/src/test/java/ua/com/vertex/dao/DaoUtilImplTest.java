package ua.com.vertex.dao;

import org.junit.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ua.com.vertex.dao.interfaces.DaoUtilInf;
import ua.com.vertex.utils.DataNavigator;

import static org.junit.Assert.assertTrue;

public class DaoUtilImplTest {

    @Test
    public void getPagingSQLParametersTest() throws Exception {
        DaoUtilInf daoUtil = new DaoUtilImpl();
        DataNavigator dataNavigator = new DataNavigator();
        MapSqlParameterSource parameters = daoUtil.getPagingSQLParameters(dataNavigator);

        assertTrue(parameters.hasValue("from"));
        assertTrue(parameters.hasValue("offset"));
    }

}
