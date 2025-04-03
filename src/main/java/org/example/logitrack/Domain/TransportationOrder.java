package org.example.logitrack.Domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class TransportationOrder {

    private final String id;
    private OrderStatus orderStatus;
    private DeliveryRoute deliveryRoute;
    private final List<Message> messages;
    private LocalDateTime lastActivityTime;


    public TransportationOrder(DeliveryRoute deliveryRoute) {
        if (deliveryRoute == null) {
            throw new IllegalArgumentException("Маршрут не найден");
        }
        this.id = UUID.randomUUID().toString();
        this.deliveryRoute = deliveryRoute;
        this.orderStatus = new OrderStatus(OrderStatus.Status.ACTIVE);
        this.messages = new ArrayList<>();
        this.lastActivityTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public DeliveryRoute getDeliveryRoute() {
        return deliveryRoute;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public LocalDateTime getLastActivityTime() {
        return lastActivityTime;
    }


    public void addMessage(Message message) {
        if (!orderStatus.isActive()) {
            throw new IllegalStateException("Невозможно добавить сообщение. Заявка неактивна");
        }
        if (message == null) {
            throw new IllegalArgumentException("Сообщение не может быть пустым");
        }
        messages.add(message);
        updateActivity();
        System.out.println("Сообщение добавлено: " + message);
    }


    public void updateActivity() {
        lastActivityTime = LocalDateTime.now();
    }


    public void checkAndCloseIfInactive() {
        if (orderStatus.isActive() && Duration.between(lastActivityTime, LocalDateTime.now()).toSeconds() >= 1) {
            close();
            System.out.println("Заявка " + id + " неактивна. Автоматическое закрытие заявки");
        }
    }


    public void close() {
        this.orderStatus = new OrderStatus(OrderStatus.Status.CLOSED);
    }


    public void reopen() {
        if (orderStatus.isActive()) {
            throw new IllegalStateException("Заявка уже активна");
        }
        this.orderStatus = new OrderStatus(OrderStatus.Status.ACTIVE);
        updateActivity();
    }

    @Override
    public String toString() {
        return "TransportationOrder{" +
                "id='" + id + '\'' +
                ", orderStatus=" + orderStatus +
                ", deliveryRoute=" + deliveryRoute +
                ", messages=" + messages +
                ", lastActivityTime=" + lastActivityTime +
                '}';
    }

}