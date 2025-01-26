package org.sakaiproject.rollcall.tool.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.Model; // For Model.of() in Wicket
import java.util.Arrays; // For Arrays.asList()
import java.util.List;


/**
 * An example page
 * 
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class FirstPage extends BasePage {

	private static final String DATE_FORMAT="dd-MMM-yyyy";
	private static final String TIME_FORMAT="HH:mm:ss";
	
	
	public FirstPage() {
		disableLink(firstLink);
		
		//name
		add(new Label("userDisplayName", sakaiProxy.getCurrentUserDisplayName()));
		
		//time
		Date d = new Date();
		String date = new SimpleDateFormat(DATE_FORMAT).format(d);
		String time = new SimpleDateFormat(TIME_FORMAT).format(d);

		add(new Label("time", new StringResourceModel("the.time", (Component) null).setParameters(date, time)));

		String siteId = sakaiProxy.getCurrentSiteId();
		add(new Label("siteId", siteId));

		List<org.sakaiproject.bbb.api.storage.BBBMeeting> meetings = bbbMeetingProxy.fetchMeetingDetails(siteId);
		String fisrtMeeting = String.valueOf(meetings.size());
		add(new Label("meetingId", fisrtMeeting));
	}
}
