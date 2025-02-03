package org.sakaiproject.rollcall.tool.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model; // For Model.of() in Wicket

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays; // For Arrays.asList()
import java.util.List;

import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.util.file.File;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.sakaiproject.rollcall.tool.model.AttendanceTime;
import org.sakaiproject.rollcall.tool.model.Attendant;


/**
 * An example page
 *
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class CourseStatisticPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public CourseStatisticPage() {
		disableLink(this.statisticLink);
		List<Attendant> attendantList = setAttendee();
		byte[] temp = setAttendanceTime(attendantList);

		add(new Image("attendanceTimeChartImage", new DynamicImageResource() {

			@Override
			protected byte[] getImageData(Attributes attributes) {
				return temp;
			}
		}));
	}


	private List<Attendant> setAttendee() {
		List<Attendant> attendantList = Arrays.asList(
				new Attendant(1L, "Lisa", "Tester"),
				new Attendant(2L, "Markus", "Muster"),
				new Attendant(3L, "Jakob", "Maier")
		);

		ListView<Attendant> listViewAttendant = new ListView<>("attendantList", attendantList) {
			@Override
			protected void populateItem(ListItem<Attendant> item) {
				item.add(new Label("number", new PropertyModel<>(item.getModel(), "id")));
				item.add(new Label("firstname", new PropertyModel<>(item.getModel(), "firstname")));
				item.add(new Label("lastname", new PropertyModel<>(item.getModel(), "lastname")));
			}
		};

		add(listViewAttendant);

		return attendantList;
	}

	private byte[] setAttendanceTime(List<Attendant> attendantList) {
		List<AttendanceTime> attendanceTimeList = Arrays.asList(
				new AttendanceTime(1L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(5))),
				new AttendanceTime(2L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(10))),
				new AttendanceTime(3L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(15))),
				new AttendanceTime(4L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(20))),
				new AttendanceTime(5L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(25))),
				new AttendanceTime(6L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(30))),
				new AttendanceTime(7L, 1L, 1L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(35))),
				new AttendanceTime(8L, 1L, 2L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(5))),
				new AttendanceTime(9L, 1L, 2L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(10))),
				new AttendanceTime(10L, 1L, 2L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(15))),
				new AttendanceTime(11L, 1L, 2L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(20))),
				new AttendanceTime(12l, 1L, 2L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(25))),
				new AttendanceTime(15L, 1L, 3L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(5))),
				new AttendanceTime(16L, 1L, 3L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(10))),
				new AttendanceTime(17L, 1L, 3L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(15))),
				new AttendanceTime(20L, 1L, 3L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(30))),
				new AttendanceTime(21L, 1L, 3L, LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(35)))
		);

		ListView<AttendanceTime> listViewAttendantTime = new ListView<>("attendanceTimeList", attendanceTimeList) {
			@Override
			protected void populateItem(ListItem<AttendanceTime> item) {
				item.add(new Label("studentId", new PropertyModel<>(item.getModel(), "studentId")));
				item.add(new Label("attendanceTime", new PropertyModel<>(item.getModel(), "attendanceTime")));
			}
		};

		add(listViewAttendantTime);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Attendant attendant : attendantList) {
			for (AttendanceTime time : attendanceTimeList) {
				if (time.getStudentId() == attendant.getId()) {
					dataset.addValue(1, attendant.getFirstname(), time.getAttendanceTime());
				}
			}
		}

		// Create the bar chart
		JFreeChart attendanceTimeLineChart = ChartFactory.createLineChart(
				"Anwesende per Zeiteinheit",         // Chart title
				"Zeit",                 // X-axis label
				"Anzahl Anwesende",                    // Y-axis label
				dataset                    // Dataset
		);

		// Convert chart to image
		BufferedImage image = attendanceTimeLineChart.createBufferedImage(600, 400);
		return toByteArray(image);
	}

	private byte[] toByteArray(BufferedImage image) {
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream()) {
			javax.imageio.ImageIO.write(image, "png", baos);
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}