package org.sakaiproject.rollcall.impl;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.db.api.SqlService;

import java.sql.SQLException;
import java.util.List;

public class RollcallDao {
    @SpringBean
    private SqlService sqlService;

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    public List<String> getRollcalls() {
        String sql = "SELECT name FROM rollcall";
        return sqlService.dbRead(sql, null, resultSet -> {
            try {
                return resultSet.getString("name");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
