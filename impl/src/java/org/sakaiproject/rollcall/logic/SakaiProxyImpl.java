package org.sakaiproject.rollcall.logic;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.sakaiproject.authz.api.*;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Group;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.*;


/**
 * Minimal implementation of the SakaiProxy API for a Rollcall tool.
 */
@Setter
@Getter
@Slf4j
public class SakaiProxyImpl implements SakaiProxy {

    // -------------------------------
    // Injections (populated via Spring)
    // -------------------------------

    private ToolManager toolManager;
    private SessionManager sessionManager;
    private UserDirectoryService userDirectoryService;
    private SecurityService securityService;
    private EventTrackingService eventTrackingService;
    private ServerConfigurationService serverConfigurationService;

    /**
     * Called when this bean starts up.
     */
    public void init() {
        log.debug("SakaiProxyImpl :: init()");
    }

    /**
     * Get the current site ID (context).
     * @return site ID of current placement
     */
    @Override
    public String getCurrentSiteId() {
        return toolManager.getCurrentPlacement().getContext();
    }

    /**
     * Get the current user.
     * @return User object, or null if none
     */
    @Override
    public User getCurrentUser() {
        String userId = getCurrentUserId();
        if (userId != null) {
            try {
                return userDirectoryService.getUser(userId);
            } catch (UserNotDefinedException e) {
                log.error("Unable to get current user by ID: " + userId, e);
            }
        }
        return null;
    }

    /**
     * Get the current user’s Sakai ID (not EID).
     * @return user ID, or null if no user is logged in
     */
    @Override
    public String getCurrentUserId() {
        return sessionManager.getCurrentSessionUserId();
    }

    /**
     * Get the current user’s display name.
     * @return display name, or empty string if no user
     */
    @Override
    public String getCurrentUserDisplayName() {
        User currentUser = getCurrentUser();
        return currentUser != null ? currentUser.getDisplayName() : "";
    }

    /**
     * Check if the current user is a super user (i.e., Sakai admin).
     * @return true if super user, false otherwise
     */
    @Override
    public boolean isSuperUser() {
        return securityService.isSuperUser();
    }

    /**
     * Post an event to Sakai’s EventTrackingService.
     * @param event event name
     * @param reference reference (context) for the event
     * @param modify whether or not this is a “modify” event
     */
    @Override
    public void postEvent(String event, String reference, boolean modify) {
        eventTrackingService.post(eventTrackingService.newEvent(event, reference, modify));
    }

    /**
     * Retrieve a boolean parameter from sakai.properties
     * @param param property name
     * @param dflt default value if not found
     * @return the property value or the default
     */
    @Override
    public boolean getConfigParam(String param, boolean dflt) {
        return serverConfigurationService.getBoolean(param, dflt);
    }

    /**
     * Retrieve a string parameter from sakai.properties
     * @param param property name
     * @param dflt default value if not found
     * @return the property value or the default
     */
    @Override
    public String getConfigParam(String param, String dflt) {
        return serverConfigurationService.getString(param, dflt);
    }

}
