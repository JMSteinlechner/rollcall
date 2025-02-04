package org.sakaiproject.rollcall.tool.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.sakaiproject.rollcall.tool.model.AttendanceTime;
import org.sakaiproject.rollcall.tool.model.Attendant;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseStatisticPage extends BasePage {

	private static final long serialVersionUID = 1L;


	public CourseStatisticPage() {
		disableLink(this.statisticLink);

		List<Attendant> attendantList = loadAttendantsFromDB();
		List<AttendanceTime> attendanceTimeList = loadAttendanceTimesFromDB();
		byte[] temp = createAttendanceChart(attendantList, attendanceTimeList);

		setupAttendantTable(attendantList);
		setupAttendanceTimeTable(attendanceTimeList);

		add(new Image("attendanceTimeChartImage", new DynamicImageResource() {
			@Override
			protected byte[] getImageData(Attributes attributes) {
				return temp;
			}
		}));

		createAttendanceChart(attendantList, attendanceTimeList);
	}

	private List<Attendant> loadAttendantsFromDB() {
		String sql = "SELECT id, firstname, lastname FROM attendants";
		return sqlServiceProxy.fetchMultipleResults(sql, new Object[]{}, resultSet -> {
			try {
				return new Attendant(
						resultSet.getLong("id"),
						resultSet.getString("firstname"),
						resultSet.getString("lastname")
				);
			} catch (SQLException e) {
				return null;
			}
		});
	}

	private List<AttendanceTime> loadAttendanceTimesFromDB() {
		String sql = "SELECT id, student_id, course_id, attendance_time FROM attendance";
		return sqlServiceProxy.fetchMultipleResults(sql, new Object[]{}, resultSet -> {
			try {
				return new AttendanceTime(
						resultSet.getLong("id"),
						resultSet.getLong("student_id"),
						resultSet.getLong("course_id"),
						resultSet.getTimestamp("attendance_time").toLocalDateTime()
				);
			} catch (SQLException e) {
				return null;
			}
		});
	}

	private void setupAttendantTable(List<Attendant> attendantList) {
		ListView<Attendant> listViewAttendant = new ListView<>("attendantList", attendantList) {
			@Override
			protected void populateItem(ListItem<Attendant> item) {
				item.add(new Label("number", new PropertyModel<>(item.getModel(), "id")));
				item.add(new Label("firstname", new PropertyModel<>(item.getModel(), "firstname")));
				item.add(new Label("lastname", new PropertyModel<>(item.getModel(), "lastname")));
			}
		};
		add(listViewAttendant);
	}

	private void setupAttendanceTimeTable(List<AttendanceTime> attendanceTimeList) {
		ListView<AttendanceTime> listViewAttendanceTime = new ListView<>("attendanceTimeList", attendanceTimeList) {
			@Override
			protected void populateItem(ListItem<AttendanceTime> item) {
				item.add(new Label("studentId", new PropertyModel<>(item.getModel(), "studentId")));
				item.add(new Label("attendanceTime", new PropertyModel<>(item.getModel(), "attendanceTime")));
			}
		};
		add(listViewAttendanceTime);
	}

	private byte[] createAttendanceChart(List<Attendant> attendantList, List<AttendanceTime> attendanceTimeList) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		List<Long> studentNumbers = new ArrayList<Long>();

		for (Attendant al: attendantList){
			studentNumbers.add(al.getId());
		}


		Map<LocalDateTime, Long> attendanceCountByDateTime = new HashMap<>();
		for(AttendanceTime atl: attendanceTimeList){
			if(atl.getCourseId() == attendanceTimeList.get(0).getCourseId()
					&& studentNumbers.contains(atl.getStudentId())) {
				if(attendanceCountByDateTime.containsKey(atl.getAttendanceTime())){
					attendanceCountByDateTime.put(atl.getAttendanceTime(), attendanceCountByDateTime.get(atl.getAttendanceTime())+1);
				}else{
					attendanceCountByDateTime.put(atl.getAttendanceTime(),1L);
				}
			}
		}

		for(LocalDateTime time: attendanceCountByDateTime.keySet()){
			dataset.addValue(time.toEpochSecond(ZoneOffset.UTC),"Anwesende", attendanceCountByDateTime.get(time));
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
