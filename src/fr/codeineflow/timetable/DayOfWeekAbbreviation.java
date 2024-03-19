package fr.codeineflow.timetable;

enum DayOfWeekAbbreviation {
	MONDAY("M"), TUESDAY("Tu"), WEDNESDAY("W"), THURSDAY("Th"), FRIDAY("F"), SATURDAY("Sa"), SUNDAY("Su");

	private final String abbreviation;
	
	DayOfWeekAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
}
