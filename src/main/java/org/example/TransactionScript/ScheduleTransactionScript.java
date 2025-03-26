package org.example.TransactionScript;

import org.example.ActiveRecord.Driver;
import org.example.ActiveRecord.DriverRepository;
import org.example.ActiveRecord.Shift;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleTransactionScript {

    // Форматтер для красивого отображения даты
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final DriverRepository repository;

    public ScheduleTransactionScript(DriverRepository repository) {
        this.repository = repository;
    }

    public void addShiftToDriver(int driverId, LocalDateTime start, LocalDateTime end) {
        Driver driver = repository.findById(driverId);
        if (!driver.isActive()) {
            throw new IllegalArgumentException("Водитель сейчас не активен. Добавление смены невозможно.");
        }
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        try {
            System.out.println("Начало транзакции добавления смены.");
            driver.addShift(new Shift(start, end));
            repository.save(driver);
            System.out.println("Транзакция успешно завершена: Смена добавлена.");
        } catch (Exception e) {
            System.err.println("Ошибка транзакции: " + e.getMessage());
        }
    }

    public List<Shift> getShiftsForDriverOnDate(int driverId, LocalDateTime date) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        System.out.println("Получение списка смен для водителя на дату: " + date.toLocalDate());
        return driver.getShiftsForDate(date);
    }

    public boolean checkDriverAvailability(int driverId, LocalDateTime dateTime) {
        Driver driver = repository.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Водитель с ID " + driverId + " не найден.");
        }
        System.out.println("Проверка доступности водителя на момент: " + dateTime.format(formatter));
        return driver.isAvailableAt(dateTime);
    }
}