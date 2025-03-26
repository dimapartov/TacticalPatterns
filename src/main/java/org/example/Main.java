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
        LocalDateTime dateTimeForShift1Start = LocalDateTime.of(2025, 3, 25, 8, 0);
        LocalDateTime dateTimeForShift1End = LocalDateTime.of(2025, 3, 25, 17, 0);

        // Создание дат для второй смены
        LocalDateTime dateTimeForShift2Start = LocalDateTime.of(2025, 3, 25, 18, 0);
        LocalDateTime dateTimeForShift2End = LocalDateTime.of(2025, 3, 25, 22, 0);

        // Создание репозитория и регистрация водителя
        DriverRepository repository = new DriverRepository();
        Driver driver = DriverFactory.createDriver("Ivan Petrov", true);
        repository.save(driver);

        // Создание транзакционного скрипта для управления расписанием
        ScheduleTransactionScript transactionScript = new ScheduleTransactionScript(repository);

        // Добавление валидных смен водителя
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift1Start, dateTimeForShift1End);
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift2Start, dateTimeForShift2End);

/*
        // Демонстрация возникновения ошибки: попытка добавить пересекающуюся смену
        System.out.println("\nПопытка добавить пересекающуюся смену:");
        transactionScript.addShiftToDriver(driver.getId(), LocalDateTime.of(2023, 12, 14, 11, 0), LocalDateTime.of(2023, 12, 14, 14, 0));

        // Демонстрация возникновения ошибки: попытка добавить смену для несуществующего водителя
        System.out.println("\nПопытка добавить смену для несуществующего водителя:");
        transactionScript.addShiftToDriver(999, // несуществующий ID
                dateTimeForShift1Start, dateTimeForShift1End);
*/

        // Получение смен на определённую дату
        List<Shift> shifts = transactionScript.getShiftsForDriverOnDate(driver.getId(), dateTimeForShift2End);
        System.out.println("\nСмены на " + dateTimeForShift2End.toLocalDate() + ": " + shifts);

        // Проверка доступности водителя в конкретное время
        boolean available = transactionScript.checkDriverAvailability(driver.getId(), LocalDateTime.of(2025, 3, 26, 23, 10));
        System.out.println("\nДоступность водителя на " + LocalDateTime.of(2025, 3, 26, 23, 10) + ": " + available);
    }

}