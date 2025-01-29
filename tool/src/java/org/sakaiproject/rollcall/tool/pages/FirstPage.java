package org.sakaiproject.rollcall.tool.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.markup.html.form.Form;


/**
 * An example page
 * 
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class FirstPage extends BasePage {

	private static final String DATE_FORMAT="dd-MMM-yyyy";
	private static final String TIME_FORMAT="HH:mm:ss";

	private static final long serialVersionUID = 1L;
	
	public FirstPage() {
		disableLink(this.firstLink);
		
		//name
		add(new Label("userDisplayName", sakaiProxy.getCurrentUserDisplayName()));
		
		//time
		Date d = new Date();
		String date = new SimpleDateFormat(DATE_FORMAT).format(d);
		String time = new SimpleDateFormat(TIME_FORMAT).format(d);

		add(new Label("time", new StringResourceModel("the.time", (Component) null).setParameters(date, time)));
		// Formular für das Speichern in der Datenbank
		Form<Void> saveForm = new Form<Void>("saveForm") {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				saveToDatabase();
			}
		};
		add(saveForm);
	}

	private void saveToDatabase() {
		try {
			// Beispiel: Speichert einen festen Wert in die Datenbank
			String sql = "INSERT INTO example_table (column_name) VALUES ('Beispieldaten')";
			sqlServiceProxy.executeQuery(sql);
			// Erfolgsmeldung (falls erforderlich)
			info("Eintrag wurde erfolgreich gespeichert!");
		} catch (Exception e) {
			error("Fehler beim Speichern in die Datenbank: " + e.getMessage());
		}
	}
}
