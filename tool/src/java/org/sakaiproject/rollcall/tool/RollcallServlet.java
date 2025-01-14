package org.sakaiproject.rollcall.tool;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.rollcall.api.RollcallService;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RollcallServlet extends HttpServlet {

    private transient RollcallService rollcallService;

    public void init() throws ServletException {
        try {
            rollcallService = (RollcallService) ComponentManager.get(RollcallService.class);
            if (rollcallService == null) {
                throw new ServletException("RollcallService not found in ComponentManager");
            }
        } catch (Exception e) {
            throw new ServletException("Error initializing RollcallServlet", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(rollcallService.getGreeting());
    }
}
