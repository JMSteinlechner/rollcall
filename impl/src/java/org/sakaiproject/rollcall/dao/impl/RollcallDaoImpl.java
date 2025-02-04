package org.sakaiproject.rollcall.dao.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.InvariantReloadingStrategy;
import org.apache.log4j.Logger;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.rollcall.dao.RollcallDao;
import org.sakaiproject.rollcall.model.Thing;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Implementation of ProjectDao
 *
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class RollcallDaoImpl extends JdbcDaoSupport implements RollcallDao {

	private static final Logger log = Logger.getLogger(RollcallDaoImpl.class);

	private PropertiesConfiguration statements;


	/**
	 * init
	 */
	public void init() {
		log.info("init()");

		//setup the vendor
		String vendor = ServerConfigurationService.getInstance().getString("vendor@org.sakaiproject.db.api.SqlService", null);

		//initialise the statements
		initStatements(vendor);

		//setup tables if we have auto.ddl enabled.
		boolean autoddl = ServerConfigurationService.getInstance().getBoolean("auto.ddl", true);
		if(autoddl) {
			initTables();
			initSQLStatements();
		}
	}

	/**
	 * Sets up our tables
	 */
	private void initTables() {
		log.info("initTables() in RollcallDaoImpl");
		try {
			getJdbcTemplate().execute(getStatement("create.example_table"));
			getJdbcTemplate().execute(getStatement("create.attendants_table"));
			getJdbcTemplate().execute(getStatement("create.attendance_table"));
		} catch (DataAccessException ex) {
			log.info("Error creating tables: " + ex.getClass() + ":" + ex.getMessage());
		}
	}

	private void initSQLStatements() {
		log.info("initSQLStatements() in RollcallDaoImpl");
		try {
			getJdbcTemplate().execute(getStatement("insert.attendant_test_data"));
			getJdbcTemplate().execute(getStatement("insert.attendance_test_data"));
		}
		catch (DataAccessException ex) {
			log.info("Error creating tables: " + ex.getClass() + ":" + ex.getMessage());
			return;
		}
	}

	/**
	 * Loads our SQL statements from the appropriate properties file

	 * @param vendor	DB vendor string. Must be one of mysql, oracle, hsqldb
	 */
	private void initStatements(String vendor) {

		URL url = getClass().getClassLoader().getResource(vendor + ".properties");

		try {
			statements = new PropertiesConfiguration(); //must use blank constructor so it doesn't parse just yet (as it will split)
			statements.setReloadingStrategy(new InvariantReloadingStrategy());	//don't watch for reloads
			statements.setThrowExceptionOnMissing(true);	//throw exception if no prop
			statements.setDelimiterParsingDisabled(true); //don't split properties
			statements.load(url); //now load our file
		} catch (ConfigurationException e) {
			log.error(e.getClass() + ": " + e.getMessage());
			return;
		}
	}

	/**
	 * Get an SQL statement for the appropriate vendor from the bundle

	 * @param key
	 * @return statement or null if none found.
	 */
	private String getStatement(String key) {
		try {
			return statements.getString(key);
		} catch (NoSuchElementException e) {
			log.error("Statement: '" + key + "' could not be found in: " + statements.getFileName());
			return null;
		}
	}
}
