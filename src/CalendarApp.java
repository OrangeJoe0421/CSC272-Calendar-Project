import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class CalendarApp {
    private ArrayList<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    // Returns all appointments (used by AppUI for filtering by date)
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    // Removes an exact appointment by object reference
    public void removeExactAppointment(Appointment toRemove) {
        appointments.remove(toRemove);
    }

    public void removeAppointment(String description, LocalDate date) {
        Iterator<Appointment> it = appointments.iterator();
        while (it.hasNext()) {
            Appointment a = it.next();
            if (a.getDate().equals(date) && a.toString().toLowerCase().contains(description.toLowerCase())) {
                it.remove();
                System.out.println("Appointment removed.");
                return;
            }
        }
        System.out.println("No matching appointment found.");
    }

    public void printAppointments(LocalDate date) {
        boolean found = false;
        for (Appointment a : appointments) {
            if (a.getDate().equals(date)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments on this date.");
        }
    }
}
