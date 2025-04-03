package org.example.logitrack;

import org.example.logitrack.Domain.Attachment;
import org.example.logitrack.Domain.DeliveryRoute;
import org.example.logitrack.Domain.Message;
import org.example.logitrack.Domain.TransportationOrder;
import org.example.logitrack.Domain.UserRole;
import org.example.logitrack.Repository.TransportationOrderRepository;
import org.example.logitrack.Service.FraudDetectionService;
import org.example.logitrack.Service.OrderLifecycleService;

import java.util.ArrayList;
import java.util.List;


public class Main {
/*
    // Демонстрация первой части
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
        LocalDateTime dateTimeForShiftTestStart = LocalDateTime.of(2025, 3, 25, 17, 0); // Старт
        LocalDateTime dateTimeForShiftTestEnd = LocalDateTime.of(2025, 3, 25, 18, 0); // Конец


        // Создание репозитория и регистрация водителя
        DriverRepository repository = new DriverRepository();
        Driver driver = DriverFactory.createDriver("Ivan Petrov", true);
        repository.save(driver);

        // Создание транзакционного скрипта для управления расписанием
        ScheduleTransactionScript transactionScript = new ScheduleTransactionScript(repository);

        // Добавление валидных смен водителя
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift1Start, dateTimeForShift1End);
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShift2Start, dateTimeForShift2End);

        // Демонстрация возникновения ошибки: попытка добавить пересекающуюся смену
        System.out.println("\nПопытка добавить пересекающуюся смену:");
        transactionScript.addShiftToDriver(driver.getId(), dateTimeForShiftTestStart, dateTimeForShiftTestEnd);

        // Демонстрация возникновения ошибки: попытка добавить смену для несуществующего водителя
        System.out.println("\nПопытка добавить смену для несуществующего водителя:");
        transactionScript.addShiftToDriver(999, // несуществующий ID
                dateTimeForShift1Start, dateTimeForShift1End);

        // Получение смен на определённую дату
        List<Shift> shifts = transactionScript.getShiftsForDriverOnDate(driver.getId(), dateTimeForShift1Start);
        System.out.println("\nСмены на " + dateTimeForShift1Start.toLocalDate() + ": " + shifts);

        // Проверка доступности водителя в конкретное время, в данном примере во время 1 смены
        boolean available = transactionScript.checkDriverAvailability(driver.getId(), dateTimeForShift1Start);
        System.out.println("\nДоступность водителя на " + dateTimeForShift1Start + ": " + available);
    }
*/

    // Демонстрация второй части
    public static void main(String[] args) throws InterruptedException {
        TransportationOrderRepository repository = new TransportationOrderRepository();
        OrderLifecycleService lifecycleService = new OrderLifecycleService(repository);
        FraudDetectionService fraudService = new FraudDetectionService();

        DeliveryRoute route = new DeliveryRoute("Центральный округ", "Южный округ");
        TransportationOrder order = lifecycleService.createOrder(route);

        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment("photo1", "накладная.pdf"));
        Message message1 = new Message("message-1", UserRole.CLIENT, "Добрый день! Отправляю фото груза и накладную", attachments);
        order.addMessage(message1);

        // Демонстрация ошибки: сообщение пытается подтвердить та же сторона
        try {
            message1.confirmMessage(UserRole.CLIENT);
        } catch (Exception e) {
            System.out.println("Ошибка подтверждения: " + e.getMessage());
        }

        try {
            message1.confirmMessage(UserRole.PROVIDER);
        } catch (Exception e) {
            System.out.println("Error during confirmation: " + e.getMessage());
        }

        System.out.println("Демонстрация неактивности заявки:");
        Thread.sleep(1000);
        order.checkAndCloseIfInactive();
        System.out.println("Статус заявки после неактивности: " + order.getOrderStatus());


        fraudService.checkForFraud(order);


        lifecycleService.changeStatus(order, "ACTIVE");
        System.out.println("Пользователь продлил заявку: " + order.getOrderStatus());

        // Добавляем дополнительные сообщения для эмуляции подозрительной активности
        Message message2 = new Message("message-2", UserRole.CLIENT, "Additional message 1", new ArrayList<>());
        Message message3 = new Message("message-3", UserRole.CLIENT, "Additional message 2", new ArrayList<>());
        Message message4 = new Message("message-4", UserRole.CLIENT, "Additional message 3", new ArrayList<>());
        order.addMessage(message2);
        order.addMessage(message3);
        order.addMessage(message4);

        // Повторная проверка на мошенничество после добавления сообщений
        fraudService.checkForFraud(order);
    }

}