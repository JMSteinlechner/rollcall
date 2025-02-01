package org.sakaiproject.rollcall.logic;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.db.api.SqlService;


public class SqlServiceProxyImpl implements SqlServiceProxy {
    private static final Logger log = Logger.getLogger(SqlServiceProxyImpl.class);

    @Setter
    private SqlService sqlService;

    @Override
    public void executeQuery(String sql) {
        log.info("Executing query: {" + sql +"}");
        try {
            sqlService.dbWrite(sql);
        } catch (Exception e) {
            log.error("Error executing query", e);
        }
    }

    @Override
    public String fetchSingleResult(String sql) {
        log.info("Fetching single result for query: {" + sql +"}");
        try {
            return sqlService.dbRead(sql).get(0).toString();
        } catch (Exception e) {
            log.error("Error fetching single result", e);
            return null;
        }
    }

    public void init() {
        log.info("SqlServiceProxyImpl initialized");
    }
}
