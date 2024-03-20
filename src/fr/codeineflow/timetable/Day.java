package fr.codeineflow.timetable;

import java.time.LocalDate;

class Day {
	private LocalDate date;
	private String dayOfWeek;

	public Day(LocalDate date) {
		this.date = date;
		this.dayOfWeek = date.getDayOfWeek().toString();
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	@Override
	public String toString() {
		return "\nDate: " + this.date.toString() + " - " + this.dayOfWeek;
	}
}
