package org.sakaiproject.rollcall.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.sakaiproject.rollcall.dao.ProjectDao;
import org.sakaiproject.rollcall.model.Thing;
import org.springframework.stereotype.Repository;

/**
 * Implementation of ProjectDao using Hibernate
 *
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 */
@Repository
@Transactional
public class ProjectDaoImpl implements ProjectDao {

	private static final Logger log = Logger.getLogger(ProjectDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	public Thing getThing(long id) {
		if (log.isDebugEnabled()) {
			log.debug("Fetching Thing with ID: " + id);
		}
		return entityManager.find(Thing.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Thing> getThings() {
		if (log.isDebugEnabled()) {
			log.debug("Fetching all Things");
		}
		return entityManager.createQuery("FROM Thing", Thing.class).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addThing(Thing t) {
		if (log.isDebugEnabled()) {
			log.debug("Adding Thing: " + t);
		}
		try {
			entityManager.persist(t);
			log.info("Thing added successfully: " + t);
			return true;
		} catch (Exception ex) {
			log.error("Error adding Thing: " + ex.getMessage());
			return false;
		}
	}
}
