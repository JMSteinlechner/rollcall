package org.sakaiproject.rollcall.logic;

import org.sakaiproject.db.api.SqlReader;

import java.util.List;

/**
 * SqlServiceProxy Interface
 *
 * This interface defines the contract for interacting with the SQL Service.
 */
public interface SqlServiceProxy {

    /**
     * Execute a custom SQL query without parameters.
     *
     * @param sql the SQL query string
     */
    void executeQuery(String sql);

    /**
     * Execute a parameterized SQL query safely to prevent SQL injection.
     *
     * @param sql the SQL query string with placeholders (e.g., "INSERT INTO table (col1) VALUES (?)")
     * @param params the array of parameters to replace the placeholders
     */
    void executeQueryWithParams(String sql, Object[] params);

    /**
     * Retrieve a single result from the SQL query.
     *
     * @param sql the SQL query string
     * @return the result as a String or null if no result is found
     */
    String fetchSingleResult(String sql);

    /**
     * Retrieve a single result from the SQL query with parameterized inputs.
     *
     * @param sql the SQL query string with placeholders
     * @param params the array of parameters to replace the placeholders
     * @return the result as a String or null if no result is found
     */
    String fetchSingleResult(String sql, Object[] params);

    /**
     * Retrieve multiple results from the SQL query, each result as a String.
     *
     * @param sql the SQL query string
     * @return the results as a List of Strings
     */
    List<String> fetchMultipleResults(String sql);

    /**
     * Retrieve a list of objects from the SQL query.
     *
     * @param sql the SQL query string with placeholders
     * @param params the array of parameters to replace the placeholders
     * @param <T> the type of the objects returned
     * @return the results as a List of objects of type T
     */
    <T> List<T> fetchMultipleResults(String sql, Object[] params, SqlReader<T> reader);
}
