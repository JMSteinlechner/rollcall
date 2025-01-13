package org.sakaiproject.rollcall.impl;

import org.sakaiproject.rollcall.api.RollcallService;

public class RollcallServiceImpl implements RollcallService {

    @Override
    public String getGreeting() {
        return "Hello from Sakai!";
    }
}
