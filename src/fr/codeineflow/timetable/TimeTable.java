package fr.codeineflow.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class TimeTable {
	private static final String TIME_FORMAT = "HH:mm:ss";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private Scanner scanner;
	private Month currentMonth;
	private boolean running;
	private LocalDateTime dateTime;
	private DateTimeFormatter timeFormatter;

	public TimeTable() {
		scanner = new Scanner(System.in);
		currentMonth = new Month();
		running = true;
		dateTime = LocalDateTime.now();
		timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
		System.out.println("Program launched at: " + dateTime.format(timeFormatter) + "\n");
	}

	private void displayMonth() {
		StringBuilder sb = new StringBuilder();

		sb.append("      " + currentMonth.getFormattedDate()).append("\n\n");

		for (DayOfWeekAbbreviation day : DayOfWeekAbbreviation.values()) {
			sb.append(String.format(" %2s ", day.getAbbreviation()));
		}

		sb.append("\n");

		for (List<Day> week : currentMonth.getWeeks()) {
			for (Day day : week) {
				if (day != null) {
					sb.append(String.format(" %2d ", day.getDate().getDayOfMonth()));
				} else {
					sb.append(" ".repeat(4));
				}
			}

			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

	private void updateCurrentMonth(int monthOffset) {
		currentMonth = new Month(currentMonth.getFirstDayOfMonth().plusMonths(monthOffset).toString());
	}

	private void handlePrev() {
		updateCurrentMonth(-1);
	}

	private void handleNext() {
		updateCurrentMonth(1);
	}

	private Optional<LocalDate> isValidDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		try {
			LocalDate date = LocalDate.parse(dateStr, formatter);
			return Optional.of(date);
		} catch (DateTimeParseException e) {
			return Optional.empty();
		}
	}

	private void handleSearch() {
		String userInputDate;
		Optional<LocalDate> validDate;

		do {
			System.out.print("Choose a date (format: YYYY-MM-DD): ");
			userInputDate = scanner.nextLine();
			validDate = isValidDate(userInputDate);
			if (validDate.isEmpty()) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD.\n");
			}
		} while (validDate.isEmpty());

		System.out.println("Search performed at: " + dateTime.format(timeFormatter) + "\n");
		currentMonth = new Month(userInputDate);
	}

	private void handleQuit() {
		System.out.println("The program has been stopped.");
		running = false;
	}

	private void handleUserInput() {
		System.out.println("[p]revious, [n]ext, [s]earch, [q]uit");
		String input = scanner.nextLine().trim().toLowerCase();

		switch (input) {
		case "p":
			handlePrev();
			break;
		case "n":
			handleNext();
			break;
		case "s":
			handleSearch();
			break;
		case "q":
			handleQuit();
			break;
		default:
			System.out.println("Unknown command. Please try again!");
		}
	}

	private void init() {
		while (running) {
			displayMonth();
			handleUserInput();
		}

		scanner.close();
	}

	public static void main(String[] args) {
		TimeTable timeTable = new TimeTable();
		timeTable.init();
	}
};
