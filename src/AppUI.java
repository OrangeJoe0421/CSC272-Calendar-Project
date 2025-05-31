import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AppUI {
    private CalendarApp calendar = new CalendarApp();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Appointment Calendar ===");
            System.out.println("1. Add Upcoming Appointment");
            System.out.println("2. Log Historical Appointment");
            System.out.println("3. Remove Appointment");
            System.out.println("4. View Appointments for a Date");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addAppointment(false);
                    break;
                case "2":
                    addAppointment(true);
                    break;
                case "3":
                    removeAppointment();
                    break;
                case "4":
                    viewAppointments();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("Exiting Appointment Calendar.");
    }

    private void addAppointment(boolean isHistorical) {
        LocalDate date = null;
        LocalTime start = null;
        LocalTime end = null;
        String desc = "";

        while (true) {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        while (true) {
            try {
                System.out.print("Enter start time (HH:MM): ");
                start = LocalTime.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:MM (e.g., 13:00).");
            }
        }

        while (true) {
            try {
                System.out.print("Enter end time (HH:MM): ");
                end = LocalTime.parse(scanner.nextLine());
                if (end.isBefore(start)) {
                    System.out.println("End time must be after start time.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid time format. Please use HH:MM.");
            }
        }

        while (true) {
            System.out.print("Enter description: ");
            desc = scanner.nextLine().trim();
            if (!desc.isEmpty()) break;
            System.out.println("Description cannot be empty.");
        }

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        boolean inPast = date.isBefore(today) || (date.equals(today) && start.isBefore(now));
        boolean inFuture = date.isAfter(today) || (date.equals(today) && start.isAfter(now));

        if (!isHistorical && inPast) {
            System.out.println("This appears to be a historical appointment.");
            System.out.println("Please use option 2 from the main menu to log this type of appointment.");
            return;
        } else if (isHistorical && !inPast) {
            System.out.println("Historical appointments must be in the past.");
            System.out.println("If this is a future appointment, please use option 1.");
            return;
        }

        calendar.addAppointment(new Appointment(date, start, end, desc, isHistorical));
        System.out.println("Appointment added.");
    }

    private void removeAppointment() {
        LocalDate date = null;

        while (true) {
            try {
                System.out.print("Enter the date of the appointment to cancel (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        ArrayList<Appointment> matches = new ArrayList<>();
        for (Appointment a : calendar.getAppointments()) {
            if (a.getDate().equals(date)) {
                matches.add(a);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No appointments found on that date.");
            return;
        }

        System.out.println("\nAppointments on " + date + ":");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }

        int selection = -1;
        while (true) {
            System.out.print("Enter the number of the appointment to cancel: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
                if (selection < 1 || selection > matches.size()) {
                    System.out.println("Invalid number. Please select a number from the list.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        Appointment selected = matches.get(selection - 1);
        System.out.println("You selected: " + selected);
        System.out.print("Are you sure you want to cancel this appointment? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y")) {
            calendar.removeExactAppointment(selected);
            System.out.println("Appointment removed.");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    private void viewAppointments() {
        LocalDate date = null;

        while (true) {
            try {
                System.out.print("Enter date to view appointments (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        calendar.printAppointments(date);
    }
}
