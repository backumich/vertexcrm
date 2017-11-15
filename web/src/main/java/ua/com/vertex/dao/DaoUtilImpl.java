package ua.com.vertex.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ua.com.vertex.dao.interfaces.DaoUtilInf;
import ua.com.vertex.utils.DataNavigator;

@Repository
public class DaoUtilImpl implements DaoUtilInf {

    @Override
    public MapSqlParameterSource getPagingSQLParameters(DataNavigator dataNavigator) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("from", (dataNavigator.getCurrentNumberPage() - 1) * dataNavigator.getRowPerPage());
        parameters.addValue("offset", dataNavigator.getRowPerPage());
        return parameters;
    }

}
