package org.sakaiproject.rollcall.tool;

import org.sakaiproject.rollcall.api.RollcallService;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RollcallServlet extends HttpServlet {

    private transient RollcallService rollcallService;

    @Override
    public void init() throws ServletException {
        // Typically you’d look up the service from the Sakai component manager
        rollcallService = (RollcallService) org.sakaiproject.component.cover.ComponentManager
                .get(RollcallService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(rollcallService.getGreeting());
    }
}
