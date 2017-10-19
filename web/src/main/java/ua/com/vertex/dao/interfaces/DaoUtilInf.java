package ua.com.vertex.dao.interfaces;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ua.com.vertex.utils.DataNavigator;

public interface DaoUtilInf {

    MapSqlParameterSource getPagingSQLParameters(DataNavigator dataNavigator);

}
