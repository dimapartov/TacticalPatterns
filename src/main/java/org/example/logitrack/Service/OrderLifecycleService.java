package org.example.logitrack.Service;

import org.example.logitrack.Domain.DeliveryRoute;
import org.example.logitrack.Domain.TransportationOrder;
import org.example.logitrack.Repository.TransportationOrderRepository;


/**
 * Доменный сервис для управления жизненным циклом заявки.
 * Реализует операции создания, изменения статуса, закрытия и переоткрытия заявки.
 */
public class OrderLifecycleService {

    private final TransportationOrderRepository repository;

    public OrderLifecycleService(TransportationOrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Создание новой заявки с заданным маршрутом доставки.
     */
    public TransportationOrder createOrder(DeliveryRoute deliveryRoute) {
        TransportationOrder order = new TransportationOrder(deliveryRoute);
        repository.save(order);
        System.out.println("Order created with id: " + order.getId());
        return order;
    }

    /**
     * Изменение статуса заявки.
     * Если передан статус CLOSED или ACTIVE, заявка закрывается или переоткрывается соответственно.
     */
    public void changeStatus(TransportationOrder order, String newStatus) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if ("CLOSED".equalsIgnoreCase(newStatus)) {
            order.close();
            System.out.println("Order " + order.getId() + " manually closed.");
        } else if ("ACTIVE".equalsIgnoreCase(newStatus)) {
            try {
                order.reopen();
                System.out.println("Order " + order.getId() + " reopened.");
            } catch (IllegalStateException e) {
                System.out.println("Order " + order.getId() + " is already active.");
            }
        } else {
            throw new IllegalArgumentException("Unknown status: " + newStatus);
        }
        repository.save(order);
    }

    /**
     * Ручное закрытие заявки.
     */
    public void closeOrder(TransportationOrder order) {
        order.close();
        repository.save(order);
        System.out.println("Order " + order.getId() + " closed.");
    }

    /**
     * Ручное переоткрытие заявки.
     */
    public void reopenOrder(TransportationOrder order) {
        order.reopen();
        repository.save(order);
        System.out.println("Order " + order.getId() + " reopened.");
    }

    /**
     * Проверка неактивности заявки и автоматическое закрытие,
     * если с момента последней активности прошло более 10 минут.
     */
    public void checkAndCloseInactiveOrder(TransportationOrder order) {
        order.checkAndCloseIfInactive();
        repository.save(order);
    }

}