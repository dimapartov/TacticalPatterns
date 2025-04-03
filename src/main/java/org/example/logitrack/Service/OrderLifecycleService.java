package org.example.logitrack.Service;

import org.example.logitrack.Domain.DeliveryRoute;
import org.example.logitrack.Domain.TransportationOrder;
import org.example.logitrack.Repository.TransportationOrderRepository;


public class OrderLifecycleService {

    private final TransportationOrderRepository repository;

    public OrderLifecycleService(TransportationOrderRepository repository) {
        this.repository = repository;
    }


    public TransportationOrder createOrder(DeliveryRoute deliveryRoute) {
        TransportationOrder order = new TransportationOrder(deliveryRoute);
        repository.save(order);
        System.out.println("Заявка создана: " + order.getId());
        return order;
    }


    public void changeStatus(TransportationOrder order, String newStatus) {
        if (order == null) {
            throw new IllegalArgumentException("Заявка не найдена");
        }
        if ("CLOSED".equalsIgnoreCase(newStatus)) {
            order.close();
            System.out.println("Заявка " + order.getId() + " закрыта");
        } else if ("ACTIVE".equalsIgnoreCase(newStatus)) {
            try {
                order.reopen();
                System.out.println("Заявка " + order.getId() + " возобновлена");
            } catch (IllegalStateException e) {
                System.out.println("Заявка " + order.getId() + " уже активна");
            }
        } else {
            throw new IllegalArgumentException("Неизвестная ошибка: " + newStatus);
        }
        repository.save(order);
    }


    public void closeOrder(TransportationOrder order) {
        order.close();
        repository.save(order);
        System.out.println("Заявка " + order.getId() + " закрыта");
    }


    public void reopenOrder(TransportationOrder order) {
        order.reopen();
        repository.save(order);
        System.out.println("Заявка " + order.getId() + " возобновлена");
    }


    public void checkAndCloseInactiveOrder(TransportationOrder order) {
        order.checkAndCloseIfInactive();
        repository.save(order);
    }

}