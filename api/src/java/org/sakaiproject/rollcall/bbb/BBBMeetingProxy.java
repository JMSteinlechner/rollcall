package org.sakaiproject.rollcall.bbb;

import java.util.List;
import java.util.Map;

public interface BBBMeetingProxy {
    public List<org.sakaiproject.bbb.api.storage.BBBMeeting> fetchMeetingDetails(String siteId);

    public Map<String, Object> getMeetingInfo(String meetingID);
}