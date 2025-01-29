package org.sakaiproject.rollcall.bbb;

import lombok.Getter;
import lombok.Setter;

import org.sakaiproject.bbb.api.BBBMeetingManager;
import org.sakaiproject.bbb.api.BBBAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BBBMeetingProxyImpl implements BBBMeetingProxy {

    @Getter @Setter
    private BBBMeetingManager bbbMeetingManager;

    @Getter @Setter
    private BBBAPI bbbApi;

    public void init() { }

    @Override
    public List<org.sakaiproject.bbb.api.storage.BBBMeeting> fetchMeetingDetails(String siteId) {
        try {
            return bbbMeetingManager.getSiteMeetings(siteId);
        } catch (Exception e){
            return new ArrayList<org.sakaiproject.bbb.api.storage.BBBMeeting>();
        }

    }

    @Override
    public Map<String, Object> getMeetingInfo(String meetingID) {
        try {
            return bbbApi.getMeetingInfo(meetingID, "");
        }
        catch (Exception e) {
            return new HashMap<String, Object>();
        }
    }
}
