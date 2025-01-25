package org.sakaiproject.rollcall.bbb;

import java.util.List;

public interface BBBMeetingProxy {
    public List<org.sakaiproject.bbb.api.storage.BBBMeeting> fetchMeetingDetails(String siteId) throws Exception;
}