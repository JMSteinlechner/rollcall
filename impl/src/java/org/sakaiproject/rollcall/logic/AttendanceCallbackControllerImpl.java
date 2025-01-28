package org.sakaiproject.rollcall.logic;

import java.util.HashMap;
import java.util.Map;

public class AttendanceCallbackControllerImpl implements AttendanceCallbackController {
    private Map<String, Object> meetingInfo;

    public AttendanceCallbackControllerImpl() {
        this.meetingInfo = new HashMap<String, Object>() {{
            put("greeting", "Hello, World!");
            put("language", "English");
        }};
    }

    public void init() {}

    public Map<String, Object> getMeetingInfo() {
        return meetingInfo;
    }

    public void setMeetingInfo(Map<String, Object> meetingInfo) {
        this.meetingInfo = meetingInfo;
    }

    @Override
    public void handleCallback(Map<String, Object> meetingInfo) {
        // TODO: implement callback when meeting ends
        //this.meetingInfo = meetingInfo;
        this.meetingInfo = new HashMap<String, Object>() {{
            put("Test", "This is a Callback");
            put("Test", "It is working");
        }};
        System.out.println("AttendanceCallbackController handleCallback");
    }

}
