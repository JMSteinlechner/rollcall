package org.sakaiproject.rollcall.logic;

import java.util.Map;

public interface AttendanceCallbackController {
    public void init();

    public Map<String, Object> getMeetingInfo();
    public void setMeetingInfo(Map<String, Object> meetingInfo);

    public void handleCallback(Map<String, Object> meetingInfo);
}
