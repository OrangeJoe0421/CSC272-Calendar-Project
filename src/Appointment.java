import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private boolean isHistorical;

    public Appointment(LocalDate date, LocalTime startTime, LocalTime endTime, String description, boolean isHistorical) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.isHistorical = isHistorical;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        String tag = isHistorical ? "[HISTORICAL] " : "";
        return tag + date + " " + startTime + " - " + endTime + " : " + description;
    }
}
