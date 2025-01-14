package org.sakaiproject.rollcall.impl;

import org.sakaiproject.rollcall.api.RollcallService;
import org.sakaiproject.rollcall.logic.RollcallLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RollcallServiceImpl implements RollcallService {

    private static final Logger log = LoggerFactory.getLogger(RollcallServiceImpl.class);
    private RollcallLogic logic;

    public void init() {
        log.debug("RollcallServiceImpl init()");
    }

    // Setter for Spring DI
    public void setLogic(RollcallLogic logic) {
        this.logic = logic;
    }
    @Override
    public String getGreeting() {
        return "Hello from Sakai!";
    }
}
