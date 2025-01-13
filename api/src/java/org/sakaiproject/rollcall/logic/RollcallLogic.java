package org.sakaiproject.rollcall.logic;

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
    void doSomething();
}
