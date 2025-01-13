package org.sakaiproject.rollcall.logic;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * A minimal logic implementation that relies on SakaiProxy
 * for any Sakai-related interactions.
 */
@Setter
@Slf4j
public class RollcallLogicImpl implements RollcallLogic {

    /**
     * Injected by Spring in components.xml
     */
    private SakaiProxy sakaiProxy;

    /**
     * Called when this bean starts up (defined via init-method="init" in components.xml)
     */
    @Override
    public void init() {
        log.debug("RollcallLogicImpl :: init()");

        // Example usage of SakaiProxy.
        // Just logs the current site ID.
        String siteId = sakaiProxy.getCurrentSiteId();
        if (siteId != null) {
            log.debug("We are in site: {}", siteId);
        } else {
            log.warn("No site context is available at startup.");
        }
    }

    /**
     * Example method to show you can call your proxy or do rollcall logic.
     */
    @Override
    public void doSomething() {
        // Minimal example:
        String currentUserId = sakaiProxy.getCurrentUserId();
        log.info("doSomething() invoked by user: {}", currentUserId);

        // Possibly do more rollcall actions here...
    }

}
