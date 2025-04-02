package org.example.logitrack.Domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * Агрегат TransportationOrder (Заявка на перевозку) – корень агрегата.
 * Инкапсулирует всю бизнес-логику, связанную с жизненным циклом заявки.
 * Содержит список сообщений, информацию о маршруте доставки, статус заявки и время последней активности.
 */
public class TransportationOrder {

    private final String id;
    private OrderStatus orderStatus;
    private DeliveryRoute deliveryRoute;
    private final List<Message> messages;
    private LocalDateTime lastActivityTime;

    /**
     * Конструктор для создания новой заявки с заданным маршрутом доставки.
     * При создании заявка получает статус ACTIVE и устанавливается время последней активности.
     */
    public TransportationOrder(DeliveryRoute deliveryRoute) {
        if (deliveryRoute == null) {
            throw new IllegalArgumentException("DeliveryRoute cannot be null");
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

    /**
     * Добавляет сообщение в заявку.
     * Инвариант: сообщение может быть добавлено только к активной заявке.
     * После добавления обновляется время последней активности.
     */
    public void addMessage(Message message) {
        if (!orderStatus.isActive()) {
            throw new IllegalStateException("Cannot add message to a closed order");
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        messages.add(message);
        updateActivity();
        System.out.println("Message added: " + message);
    }

    /**
     * Обновляет время последней активности заявки.
     */
    public void updateActivity() {
        lastActivityTime = LocalDateTime.now();
    }

    /**
     * Проверяет, прошло ли с последней активности более 10 минут.
     * Если да, то заявка автоматически закрывается.
     */
    public void checkAndCloseIfInactive() {
        if (orderStatus.isActive() && Duration.between(lastActivityTime, LocalDateTime.now()).toMinutes() >= 10) {
            close();
            System.out.println("Order " + id + " automatically closed due to inactivity.");
        }
    }

    /**
     * Метод для ручного закрытия заявки.
     */
    public void close() {
        this.orderStatus = new OrderStatus(OrderStatus.Status.CLOSED);
    }

    /**
     * Метод для переоткрытия закрытой заявки.
     */
    public void reopen() {
        if (orderStatus.isActive()) {
            throw new IllegalStateException("Order is already active");
        }
        this.orderStatus = new OrderStatus(OrderStatus.Status.ACTIVE);
        updateActivity();
    }

    @Override
    public String toString() {
        return "TransportationOrder{id='" + id + "', status=" + orderStatus + ", deliveryRoute=" + deliveryRoute + ", messages=" + messages + "}";
    }

}