package org.sakaiproject.rollcall.tool;

import org.apache.commons.logging.Log;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.IRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.Url;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import org.sakaiproject.rollcall.tool.pages.FirstPage;

/**
 * Main application class for our app
 *
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 * @author David F. Torres
 * @author Michael Mertins (mertins@zedat.fu-berlin.de)
 *
 * Cf. https://confluence.sakaiproject.org/pages/viewpage.action?pageId=83034325
 */
public class MyApplication extends WebApplication {

	/**
	 * Configure your app here
	 */
    @Override
    protected void init() {
        super.init();

        // Disable CSP header for development
        getCspSettings().blocking().disabled();

        // Configure Spring injection
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

        // Don't throw exceptions for missing properties, fallback to defaults
        getResourceSettings().setThrowExceptionOnMissingResource(false);

        // Remove Wicket-specific tags from the generated markup
        getMarkupSettings().setStripWicketTags(true);

        // On session timeout or access denial, redirect to main page
        getApplicationSettings().setPageExpiredErrorPage(FirstPage.class);
        getApplicationSettings().setAccessDeniedPage(FirstPage.class);

        // Add request cycle listener
        getRequestCycleListeners().add(new IRequestCycleListener() {
            @Override
            public void onBeginRequest(RequestCycle cycle) {
                // Optional: Log request start
            }

            @Override
            public void onEndRequest(RequestCycle cycle) {
                // Optional: Log request end
            }

            @Override
            public IRequestHandler onException(RequestCycle cycle, Exception ex) {
                return null; // Let other listeners handle the exception
            }
        });
    }



	/**
	 * The main page for our app
	 *
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<FirstPage> getHomePage() {
		return FirstPage.class;
	}


	/**
     * Constructor
     */
	public MyApplication()
	{
	}



}
