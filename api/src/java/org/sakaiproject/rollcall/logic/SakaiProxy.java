package org.sakaiproject.rollcall.logic;

import org.sakaiproject.user.api.User;

/**
 * Minimal SakaiProxy interface matching the methods
 * in the corresponding SakaiProxyImpl.
 */
public interface SakaiProxy {

    /**
     * Get current site ID.
     */
    String getCurrentSiteId();

    /**
     * Get the current user object.
     */
    User getCurrentUser();

    /**
     * Get the current user’s Sakai ID (not EID).
     */
    String getCurrentUserId();

    /**
     * Get the current user’s display name.
     */
    String getCurrentUserDisplayName();

    /**
     * Check if the current user is a super user (admin).
     */
    boolean isSuperUser();

    /**
     * Post an event to Sakai.
     */
    void postEvent(String event, String reference, boolean modify);

    /**
     * Retrieve a boolean parameter from sakai.properties.
     */
    boolean getConfigParam(String param, boolean dflt);

    /**
     * Retrieve a string parameter from sakai.properties.
     */
    String getConfigParam(String param, String dflt);
}
