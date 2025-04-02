package org.example.logitrack.Domain;

import java.util.Objects;

/**
 * Значение-объект для статуса заявки.
 * Обеспечивает инвариант неизменности после создания.
 */
public final class OrderStatus {
    public enum Status {
        ACTIVE,
        CLOSED
    }

    private final Status status;

    public OrderStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Проверяет, активна ли заявка.
     */
    public boolean isActive() {
        return status == Status.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatus)) return false;
        OrderStatus that = (OrderStatus) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return status.name();
    }
}