package org.sakaiproject.rollcall.logic;


import org.sakaiproject.rollcall.model.*;
/**
 * Minimal interface for Rollcall logic services.
 */
public interface RollcallLogic {
    /**
     * Called when this bean starts up, or to initialize.
     */
    void init();

    /**
     * Example method. Do something "rollcallish."
     */
    void doSomethingWithSite();
}
