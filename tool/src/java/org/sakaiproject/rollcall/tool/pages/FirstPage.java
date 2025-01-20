package org.sakaiproject.rollcall.tool.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sakaiproject.rollcall.impl.RollcallDao;

public class FirstPage extends BasePage {
	@SpringBean
	private RollcallDao rollcallDao; // Wird automatisch injiziert

	public void setRollcallDao(RollcallDao rollcallDao) {
		this.rollcallDao = rollcallDao;
	}

	public FirstPage() {
		// Name des Benutzers
		add(new Label("userDisplayName", sakaiProxy.getCurrentUserDisplayName()));

		// Zeit anzeigen
		Date d = new Date();
		String date = new SimpleDateFormat("dd-MMM-yyyy").format(d);
		String time = new SimpleDateFormat("HH:mm:ss").format(d);
		add(new Label("time", Model.of("Date: " + date + ", Time: " + time)));

		// Rollcall-Liste
		List<String> rollcalls = rollcallDao.getRollcalls();
		RepeatingView rollcallRepeater = new RepeatingView("rollcallList");
		for (String rollcall : rollcalls) {
			rollcallRepeater.add(new Label(rollcallRepeater.newChildId(), rollcall));
		}
		add(rollcallRepeater);
	}
}
