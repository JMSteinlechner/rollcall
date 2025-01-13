package org.sakaiproject.rollcall.model;

/**
 * Minimal placeholder for a 'RollcallSite' domain object.
 */
public class RollcallSite {

    private String siteId;

    public RollcallSite(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
