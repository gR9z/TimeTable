package fr.codeineflow.timetable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Month {
	private LocalDate parsedDate;
	private LocalDate firstDayOfMonth;
	private LocalDate lastDayOfMonth;
	private List<List<Day>> weeks = new ArrayList<>();

	public Month() {
		this(LocalDate.now().toString());
	}

	public Month(String date) {
		parsedDate = LocalDate.parse(date);
		this.firstDayOfMonth = parsedDate.with(TemporalAdjusters.firstDayOfMonth());
		this.lastDayOfMonth = parsedDate.with(TemporalAdjusters.lastDayOfMonth());
		generateMonth();
	}

	public LocalDate getFirstDayOfMonth() {
		return firstDayOfMonth;
	}

	public LocalDate getLastDayOfMonth() {
		return lastDayOfMonth;
	}

	public String getFormattedDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE yyyy/MM/dd", Locale.ENGLISH);
		return parsedDate.format(formatter);
	}
	
	public List<List<Day>> getWeeks() {
		return weeks;
	}
	
	private void generateMonth() {
		LocalDate currentDay = this.firstDayOfMonth;
		List<Day> currentWeek = new ArrayList<>();

		int offset = currentDay.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();

		for (int i = 0; i < offset; i++) {
			currentWeek.add(null);
		}

		while (!currentDay.isAfter(this.lastDayOfMonth)) {
			currentWeek.add(new Day(currentDay));

			if (currentDay.getDayOfWeek() == DayOfWeek.SUNDAY || currentDay.isEqual(this.lastDayOfMonth)) {
				weeks.add(new ArrayList<>(currentWeek));
				currentWeek.clear();
			}
			currentDay = currentDay.plusDays(1);
		}

		if (!currentWeek.isEmpty()) {
			weeks.add(currentWeek);
		}
	}
}
