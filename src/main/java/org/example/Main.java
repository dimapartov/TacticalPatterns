package org.example;


import org.example.ActiveRecord.Driver;
import org.example.ActiveRecord.DriverFactory;
import org.example.ActiveRecord.DriverRepository;
import org.example.ActiveRecord.Shift;
import org.example.TransactionScript.ScheduleTransactionScript;

import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;


public class Main {

    public static void main(String[] args) {


        // Форматтер для красивого отображения даты
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Создание дат для первой смены
        LocalDateTime dateTimeForShift1Start = LocalDateTime.of(2025, 3, 25, 8, 0); // Старт первой смены
        LocalDateTime dateTimeForShift1End = LocalDateTime.of(2025, 3, 25, 17, 0); // Конец первой смены

        // Создание дат для второй смены
        LocalDateTime dateTimeForShift2Start = LocalDateTime.of(2025, 3, 25, 18, 0); // Старт второй смены
        LocalDateTime dateTimeForShift2End = LocalDateTime.of(2025, 3, 25, 22, 0); // Конец второй смены

        // Создание дат для проверки пересекающейся смены
        LocalDateTime dateTimeForShiftTestStart = LocalDateTime.of(2025, 3, 25, 16, 0); // Старт
        LocalDateTime dateTimeForShiftTestEnd = LocalDateTime.of(2025, 3, 25, 18, 0); // Конец

        DriverRepository repository = new DriverRepository();
        Driver driver = DriverFactory.createDriver("Ivan Petrov", true);
        repository.save(driver);

        ScheduleTransactionScript transactionScript = new ScheduleTransactionScript(repository);

        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift1Start, dateTimeForShift1End);
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift2Start, dateTimeForShift2End);

        System.out.println("\nПопытка добавить пересекающуюся смену:");
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShiftTestStart, dateTimeForShiftTestEnd);

/*
        transactionScript.addShiftToDriver(999, // несуществующий ID
                dateTimeForShift1Start, dateTimeForShift1End);
*/

        List<Shift> shifts = transactionScript.getShiftsForDriverOnDate(driver.getId(), dateTimeForShift1Start);
        System.out.println("\nСмены на " + dateTimeForShift1Start.toLocalDate() + ": " + shifts);

        boolean available = transactionScript.checkDriverAvailability(driver.getId(), dateTimeForShift1Start);
        System.out.println("\nДоступность водителя на " + dateTimeForShift1Start + ": " + available);
    }

}