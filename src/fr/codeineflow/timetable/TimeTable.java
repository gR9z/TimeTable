package fr.codeineflow.timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TimeTable {
	static Scanner scanner = new Scanner(System.in);
	static Month currentMonth = new Month();
	static boolean running = true;
	static LocalDateTime launchTime = LocalDateTime.now();
	static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	private static void displayMonth(Month currentMonth) {
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
					sb.append("    ");
				}
			}

			sb.append("\n");
		}

		System.out.println(sb.toString());
	}

	public static boolean isValidDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate.parse(dateStr, formatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static void handleUserInput() {
		System.out.println("[p]revious, [n]ext, [s]earch, [q]uit");
		String input = scanner.nextLine();

		switch (input) {
		case "p":
			currentMonth = new Month(currentMonth.getFirstDayOfMonth().minusMonths(1).toString());
			break;
		case "n":
			currentMonth = new Month(currentMonth.getFirstDayOfMonth().plusMonths(1).toString());
			break;
		case "s":
			String userInputDate;
			boolean validDate;
			
			do {
				System.out.print("Choose a date (format: YYYY-MM-DD): ");
				userInputDate = scanner.nextLine();
				validDate = isValidDate(userInputDate);
				if(!validDate) {
					System.out.println("Invalid date format. Please use YYYY-MM-DD.\n");
				}
			} while (!validDate);

			LocalDateTime searchTime = LocalDateTime.now();			
				System.out.println("Search performed at: " + searchTime.format(timeFormatter) + "\n");
				currentMonth = new Month(userInputDate);
			break;
		case "q":
			running = false;
			break;
		default:
			System.out.println("Unknown command. Please try again!");
		}
	}

	public static void main(String[] args) {
		System.out.println("Program launched at: " + launchTime.format(timeFormatter) + "\n");
		
		while (running) {
			displayMonth(currentMonth);
			handleUserInput();
		}

		scanner.close();
	}
};
