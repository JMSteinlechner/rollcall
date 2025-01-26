package org.sakaiproject.rollcall.bbb;

import lombok.Getter;
import lombok.Setter;

import org.sakaiproject.bbb.api.BBBMeetingManager;

import java.util.ArrayList;
import java.util.List;

public class BBBMeetingProxyImpl implements BBBMeetingProxy {

    @Getter @Setter
    private BBBMeetingManager bbbMeetingManager;

    public void init() { }

    @Override
    public List<org.sakaiproject.bbb.api.storage.BBBMeeting> fetchMeetingDetails(String siteId) {
        try {
            return bbbMeetingManager.getSiteMeetings(siteId);
        } catch (Exception e){
            return new ArrayList<org.sakaiproject.bbb.api.storage.BBBMeeting>();
        }

    }
}
