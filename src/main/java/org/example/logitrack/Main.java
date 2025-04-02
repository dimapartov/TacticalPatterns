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


/**
 * Демонстрация работы системы LogiTrack.
 */
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
        // Создаем in-memory репозиторий и доменный сервис для управления заявками
        TransportationOrderRepository repository = new TransportationOrderRepository();
        OrderLifecycleService lifecycleService = new OrderLifecycleService(repository);
        FraudDetectionService fraudService = new FraudDetectionService();

        // Создаем новую заявку с маршрутом доставки
        DeliveryRoute route = new DeliveryRoute("New York", "Los Angeles");
        TransportationOrder order = lifecycleService.createOrder(route);

        // Создаем сообщение от клиента с вложением (вложение добавляется только на этапе создания сообщения)
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(new Attachment("att-001", "invoice.pdf"));
        Message message1 = new Message("msg-001", UserRole.CLIENT, "Please confirm the order details.", attachments);
        order.addMessage(message1);

        // Попытка подтвердить сообщение от той же стороны (должна вызвать ошибку)
        try {
            message1.confirmMessage(UserRole.CLIENT);
        } catch (Exception e) {
            System.out.println("Error during confirmation: " + e.getMessage());
        }

        // Правильное подтверждение сообщения противоположной стороной
        try {
            message1.confirmMessage(UserRole.PROVIDER);
        } catch (Exception e) {
            System.out.println("Error during confirmation: " + e.getMessage());
        }

        // Симуляция неактивности: для демонстрации автоматического закрытия заявки
        // В реальном приложении ожидание составит 10 минут, здесь используем Thread.sleep и/или корректируем время активности.
        System.out.println("Simulating inactivity for automatic order closure...");
        Thread.sleep(1000); // Используем 1 секунду для демонстрации
        order.checkAndCloseIfInactive();
        System.out.println("Order status after inactivity check: " + order.getOrderStatus());

        // Выполняем проверку на мошенничество
        fraudService.checkForFraud(order);

        // Меняем статус заявки вручную через OrderLifecycleService (переоткрытие заявки)
        lifecycleService.changeStatus(order, "ACTIVE");
        System.out.println("Order status after manual reopen: " + order.getOrderStatus());

        // Добавляем дополнительные сообщения для эмуляции подозрительной активности
        Message message2 = new Message("msg-002", UserRole.CLIENT, "Additional message 1", new ArrayList<>());
        Message message3 = new Message("msg-003", UserRole.CLIENT, "Additional message 2", new ArrayList<>());
        Message message4 = new Message("msg-004", UserRole.CLIENT, "Additional message 3", new ArrayList<>());
        order.addMessage(message2);
        order.addMessage(message3);
        order.addMessage(message4);

        // Повторная проверка на мошенничество после добавления сообщений
        fraudService.checkForFraud(order);
    }

}