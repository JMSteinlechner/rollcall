package org.sakaiproject.rollcall.bbb;

import org.sakaiproject.bbb.api.BBBMeetingManager;

import java.util.List;

public class BBBMeetingProxyImpl implements BBBMeetingProxy {

    private BBBMeetingManager bbbMeetingManager;

    public void setBbbMeetingManager(BBBMeetingManager bbbMeetingManager) {
        this.bbbMeetingManager = bbbMeetingManager;
    }

    public List<org.sakaiproject.bbb.api.storage.BBBMeeting> fetchMeetingDetails(String siteId) throws Exception {
        return bbbMeetingManager.getSiteMeetings(siteId);
    }
}
