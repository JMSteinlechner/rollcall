package org.sakaiproject.rollcall.tool.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.Model; // For Model.of() in Wicket
import java.util.Arrays; // For Arrays.asList()
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.form.Form;


/**
 * An example page
 * 
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class CourseStatistikPage extends BasePage {

	private static final String DATE_FORMAT = "dd-MMM-yyyy";
	private static final String TIME_FORMAT = "HH:mm:ss";

	private static final long serialVersionUID = 1L;
	
	public CourseStatistikPage() {
		disableLink(this.courseStatistikPage);
		List<Attendant> attendantList = setAttendee();
		setAttendanceTime(attendantList);
		}

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

	private List<Attendant> setAttendee() {
		List<Attendant> attendantList = Arrays.asList(
				new Attendant(1, "Lisa", "Tester"),
				new Attendant(2, "Markus", "Muster"),
				new Attendant(3, "Jakob", "Maier")
		);

		ListView<Attendant> listView = new ListView<Attendant>("rows", attendantList) {
			protected void populateItem(ListItem<Attendant> item) {
				Attendant attendant = item.getModelObject();
				item.add(new Label("number", new PropertyModel<>(attendant, "number")));
				item.add(new Label("firstname", new PropertyModel<>(attendant, "firstname")));
				item.add(new Label("lastname", new PropertyModel<>(attendant, "lastname")));
			}
		};
		return attendantList;
	}

	private List<AttendanceTime> setAttendanceTime(attendantList) {
		List<AttendanceTime> attendanceTimeList = Arrays.asList(

		)

	}
}
