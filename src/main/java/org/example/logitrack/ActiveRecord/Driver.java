package org.example.logitrack.ActiveRecord;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private final int id;
    private final String fullName;
    private boolean isActive;
    private final List<Shift> schedule = new ArrayList<>();

    public Driver(int id, String fullName, boolean isActive) {
        this.id = id;
        this.fullName = fullName;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void addShift(Shift shift) {
        for (Shift s : schedule) {
            if (s.overlapsWith(shift)) {
                throw new IllegalArgumentException("Смена пересекается с существующей сменой.");
            }
        }
        schedule.add(shift);
    }

    public List<Shift> getShiftsForDate(LocalDateTime date) {
        List<Shift> shiftsForDate = new ArrayList<>();
        for (Shift s : schedule) {
            if (s.isOnDate(date)) {
                shiftsForDate.add(s);
            }
        }
        return shiftsForDate;
    }

    public boolean isAvailableAt(LocalDateTime dateTime) {
        for (Shift s : schedule) {
            if (s.isDuring(dateTime)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Driver{id=" + id + ", fullName='" + fullName + "', isActive=" + isActive + "}";
    }
}