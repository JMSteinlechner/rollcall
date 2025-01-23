package org.sakaiproject.rollcall.tool;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AttendanceCallbackController {


    @EventListener
    public void handleCallback(Map<String, Object> meetingInfo) {
        // TODO: implement callback when meeting ends
        System.out.println("AttendanceCallbackController handleCallback");
    }
}