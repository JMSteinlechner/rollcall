package org.sakaiproject.rollcall.logic;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.UserDirectoryService;

/**
 * Implementation of our SakaiProxy API
 *
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class SakaiProxyImpl implements SakaiProxy {

	private static final Logger log = Logger.getLogger(SakaiProxyImpl.class);

	/**
 	* {@inheritDoc}
 	*/
	public String getCurrentSiteId(){
		return toolManager.getCurrentPlacement().getContext();
	}

	/**
 	* {@inheritDoc}
 	*/
	public String getCurrentUserId() {
		return sessionManager.getCurrentSessionUserId();
	}

	/**
 	* {@inheritDoc}
 	*/
	public String getCurrentUserDisplayName() {
	   return userDirectoryService.getCurrentUser().getDisplayName();
	}

	/**
 	* {@inheritDoc}
 	*/
	public boolean isSuperUser() {
		return securityService.isSuperUser();
	}

	/**
 	* {@inheritDoc}
 	*/
	public void postEvent(String event,String reference,boolean modify) {
		eventTrackingService.post(eventTrackingService.newEvent(event,reference,modify));
	}

	/**
 	* {@inheritDoc}
 	*/
	public boolean getConfigParam(String param, boolean dflt) {
		return serverConfigurationService.getBoolean(param, dflt);
	}

	/**
 	* {@inheritDoc}
 	*/
	public String getConfigParam(String param, String dflt) {
		return serverConfigurationService.getString(param, dflt);
	}

	/**
	 * init - perform any actions required here for when this bean starts up
	 */
	public void init() {
		log.info("init");
	}

	@Getter @Setter
	private ToolManager toolManager;

	@Getter @Setter
	private SessionManager sessionManager;

	@Getter @Setter
	private UserDirectoryService userDirectoryService;

	@Getter @Setter
	private SecurityService securityService;

	@Getter @Setter
	private EventTrackingService eventTrackingService;

	@Getter @Setter
	private ServerConfigurationService serverConfigurationService;

	@Getter @Setter
	private SiteService siteService;
}
