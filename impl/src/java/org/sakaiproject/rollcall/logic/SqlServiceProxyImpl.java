package org.sakaiproject.rollcall.logic;

import lombok.Setter;

import net.sf.ehcache.Cache;
import org.apache.log4j.Logger;
import org.sakaiproject.db.api.SqlReader;
import org.sakaiproject.db.api.SqlService;
import org.sakaiproject.rollcall.dao.ProjectDao;
import org.sakaiproject.rollcall.dao.RollcallDao;

import java.util.Collections;
import java.util.List;

public class SqlServiceProxyImpl implements SqlServiceProxy {
    private static final Logger log = Logger.getLogger(SqlServiceProxyImpl.class);

    @Setter
    private SqlService sqlService;

    @Setter
    private RollcallDao dao;

    @Setter
    private Cache cache;

    @Override
    public void executeQuery(String sql) {
        log.info("Executing query: {" + sql + "}");
        try {
            sqlService.dbWrite(sql);
        } catch (Exception e) {
            log.error("Error executing query", e);
        }
    }

    @Override
    public void executeQueryWithParams(String sql, Object[] params) {
        log.info("Executing parameterized query: {" + sql + "}");
        try {
            sqlService.dbWrite(sql, params);
        } catch (Exception e) {
            log.error("Error executing parameterized query", e);
        }
    }

    @Override
    public String fetchSingleResult(String sql) {
        log.info("Fetching single result for query: {" + sql + "}");
        try {
            List<String> results = sqlService.dbRead(sql);
            if (results.isEmpty()) {
                log.warn("No results found for query: {" + sql + "}");
                return null;
            }
            return results.get(0);
        } catch (Exception e) {
            log.error("Error fetching single result", e);
            return null;
        }
    }

    @Override
    public String fetchSingleResult(String sql, Object[] params) {
        log.info("Fetching single result with parameters for query: {" + sql + "}");
        try {
            List<String> results = sqlService.dbRead(sql, params, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                    return null;
                } catch (Exception e) {
                    return null;
                }
            });

            if (results.isEmpty()) {
                log.warn("No results found for query: {" + sql + "}");
                return null;
            }
            return results.get(0);
        } catch (Exception e) {
            log.error("Error fetching single result with parameters", e);
            return null;
        }
    }

    @Override
    public List<String> fetchMultipleResults(String sql) {
        log.info("Fetching multiple results for query: {" + sql + "}");
        try {
            return sqlService.dbRead(sql);
        } catch (Exception e) {
            log.error("Error fetching multiple results", e);
            return Collections.emptyList();
        }
    }

    @Override
    public <T> List<T> fetchMultipleResults(String sql, Object[] params, SqlReader<T> reader) {
        log.info("Fetching multiple results with parameters for query: {" + sql + "}");
        try {
            return sqlService.dbRead(sql, params, reader);
        } catch (Exception e) {
            log.error("Error fetching multiple results with parameters", e);
            return Collections.emptyList();
        }
    }

    public void init() {
        log.info("SqlServiceProxyImpl initialized");
    }
}
