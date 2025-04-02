package org.example.logitrack.ActiveRecord;

import java.time.LocalDateTime;


public class Shift {

    private final LocalDateTime start;
    private final LocalDateTime end;

    public Shift(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("Время окончания смены не может быть раньше времени начала.");
        }
        this.start = start;
        this.end = end;
    }

    public boolean overlapsWith(Shift other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    public boolean isOnDate(LocalDateTime date) {
        return start.toLocalDate().equals(date.toLocalDate());
    }

    public boolean isDuring(LocalDateTime dateTime) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    @Override
    public String toString() {
        return "Shift{start=" + start + ", end=" + end + "}";
    }

}