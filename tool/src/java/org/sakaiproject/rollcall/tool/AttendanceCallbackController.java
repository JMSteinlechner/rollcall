package org.sakaiproject.rollcall.tool;

import org.springframework.stereotype.Component;

@Component
public class AttendanceCallbackController {


    public void handleCallback(String meetingID) {
        // TODO: implement callback when meeting ends
        System.out.println("AttendanceCallbackController handleCallback");
    }
}