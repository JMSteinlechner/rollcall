package org.sakaiproject.rollcall.tool.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.portal.util.PortalUtils;

import org.sakaiproject.rollcall.logic.ProjectLogic;
import org.sakaiproject.rollcall.logic.SakaiProxy;


/**
 * This is our base page for our Sakai app. It sets up the containing markup and top navigation.
 * All top level pages should extend from this page so as to keep the same navigation. The content for those pages will
 * be rendered in the main area below the top nav.
 * 
 * <p>It also allows us to setup the API injection and any other common methods, which are then made available in the other pages.
 * 
 * @author Steve Swinsburg (steve.swinsburg@gmail.com)
 *
 */
public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(BasePage.class);
	
	@SpringBean(name="org.sakaiproject.rollcall.logic.SakaiProxy")
	protected SakaiProxy sakaiProxy;
	
	@SpringBean(name="org.sakaiproject.rollcall.logic.ProjectLogic")
	protected ProjectLogic projectLogic;
	
	Link<Void> firstLink;
	Link<Void> secondLink;
	Link<Void> thirdLink;
	
	public final FeedbackPanel feedbackPanel;

	/**
	 * The current user
	 */
	protected String currentUserUuid;


	public BasePage() {
		log.debug("BasePage()");

		// set locale (not implemented yet)
		// setUserPreferredLocale();

		// nav container
		final WebMarkupContainer nav = new WebMarkupContainer("rollcallPageNav") {
			private static final long serialVersionUID = 1L;

		};

		nav.setOutputMarkupId(true);
		//nav.setMarkupId("rollcall-navbar");

		//first link
		this.firstLink = new BookmarkablePageLink<Void>("firstLink", FirstPage.class){
			private static final long serialVersionUID = 1L;
		};
		this.firstLink.add(new Label("screenreaderlabel", getString("link.screenreader.tabnotselected")));
		nav.add(this.firstLink);

		//second link
		this.secondLink = new BookmarkablePageLink<Void>("secondLink", SecondPage.class){
			private static final long serialVersionUID = 1L;
		};
		this.secondLink.add(new Label("screenreaderlabel", getString("link.screenreader.tabnotselected")));
		nav.add(this.secondLink);

		//second link
		this.thirdLink = new BookmarkablePageLink<Void>("thirdLink", ThirdPage.class){
			private static final long serialVersionUID = 1L;
		};
		this.thirdLink.add(new Label("screenreaderlabel", getString("link.screenreader.tabnotselected")));
		nav.add(this.thirdLink);


        add(nav);
		// Add a FeedbackPanel for displaying our messages
        feedbackPanel = new FeedbackPanel("feedback"){

        	@Override
        	protected Component newMessageDisplayComponent(final String id, final FeedbackMessage message) {
        		final Component newMessageDisplayComponent = super.newMessageDisplayComponent(id, message);

        		if(message.getLevel() == FeedbackMessage.ERROR ||
        			message.getLevel() == FeedbackMessage.DEBUG ||
        			message.getLevel() == FeedbackMessage.FATAL ||
        			message.getLevel() == FeedbackMessage.WARNING){
        			add(AttributeModifier.replace("class", "alertMessage"));
        		} else if(message.getLevel() == FeedbackMessage.INFO){
        			add(AttributeModifier.replace("class", "success"));
        		}

        		return newMessageDisplayComponent;
        	}
        };
        add(feedbackPanel);

    }

	
	/**
	 * Helper to clear the feedbackpanel display.
	 * @param f	FeedBackPanel
	 */
	public void clearFeedback(FeedbackPanel f) {
		if(!f.hasFeedbackMessage()) {
			f.add(AttributeModifier.replace("class", ""));
		}
	}
	
	/**
	 * This block adds the required wrapper markup to style it like a Sakai tool. 
	 * Add to this any additional CSS or JS references that you need.
	 * 
	 */
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		final String version = PortalUtils.getCDNQuery();

		//get the Sakai skin header fragment from the request attribute
		HttpServletRequest request = (HttpServletRequest)getRequest().getContainerRequest();
		
		response.render(StringHeaderItem.forString((String)request.getAttribute("sakai.html.head")));
		response.render(OnLoadHeaderItem.forScript("setMainFrameHeight( window.name )"));

		// Tool additions (at end so we can override if required)
		response.render(StringHeaderItem
				.forString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"));
		
		//probably not necessary
		response.render(CssHeaderItem.forUrl("css/rollcall.css"));

		response.render(StringHeaderItem.forString("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />"));
		response.render(
				new PriorityHeaderItem(
						JavaScriptHeaderItem
								.forUrl(String.format("/library/webjars/jquery/1.12.4/jquery.min.js%s", version))));
		response.render(
				new PriorityHeaderItem(
						JavaScriptHeaderItem
								.forUrl(String.format("/library/webjars/jquery-ui/1.12.1/jquery-ui.min.js%s", version))));
	}
	
	
	/** 
	 * Helper to disable a link. Add the Sakai class 'current'.
	 */
	protected final void disableLink(final Link<Void> l) {
		// since the disable does not apply correctly to the disabled link we need to transfor the <a> into a span
		l.add(new AttributeAppender("class", new Model<String>("current"), " "));
		l.replace(new Label("screenreaderlabel", getString("link.screenreader.tabselected")));
		l.setEnabled(false);

		/*WebMarkupContainer spanReplacement = new WebMarkupContainer(l.getId());
		spanReplacement.add(new AttributeModifier("class", "current"));
		spanReplacement.add(new AttributeModifier("title", l.getDefaultModelObjectAsString()));

		spanReplacement.add(new Label("screenreaderlabel", getString("link.screenreader.tabselected")));

		l.getParent().replace(spanReplacement);*/
	}
	
	
	
}
