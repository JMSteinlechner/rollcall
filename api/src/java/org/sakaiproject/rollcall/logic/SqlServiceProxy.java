package org.sakaiproject.rollcall.logic;

/**
 * SqlServiceProxy Interface
 *
 * This interface defines the contract for interacting with the SQL Service.
 */
public interface SqlServiceProxy {

    /**
     * Execute a custom SQL query
     * @param sql the SQL query string
     */
    void executeQuery(String sql);

    /**
     * Another example: Retrieve a single result from the SQL query
     * @param sql the SQL query string
     * @return the result as a String
     */
    String fetchSingleResult(String sql);
}
