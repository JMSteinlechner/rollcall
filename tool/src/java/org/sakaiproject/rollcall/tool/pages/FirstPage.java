package org.sakaiproject.rollcall.tool.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.rollcall.impl.RollcallDao;

public class FirstPage extends BasePage {
	@SpringBean
	private RollcallDao rollcallDao; // Wird automatisch injiziert

	private static final String DATE_FORMAT="dd-MMM-yyyy";
	private static final String TIME_FORMAT="HH:mm:ss";

	public void setRollcallDao(RollcallDao rollcallDao) {
		this.rollcallDao = rollcallDao;
	}

	public FirstPage() {
		// Name des Benutzers
		add(new Label("userDisplayName", sakaiProxy.getCurrentUserDisplayName()));

		// Zeit anzeigen
		Date d = new Date();
		String date = new SimpleDateFormat(DATE_FORMAT).format(d);
		String time = new SimpleDateFormat(TIME_FORMAT).format(d);

		add(new Label("time", new StringResourceModel("the.time", (Component) null).setParameters(date, time)));


		// Rollcall-Liste
		List<String> rollcalls = rollcallDao.getRollcalls();
		RepeatingView rollcallRepeater = new RepeatingView("rollcallList");
		for (String rollcall : rollcalls) {
			rollcallRepeater.add(new Label(rollcallRepeater.newChildId(), rollcall));
		}
		add(rollcallRepeater);
	}
}
