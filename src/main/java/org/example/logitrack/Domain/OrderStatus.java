package org.example.logitrack.Domain;

import java.util.Objects;



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


    public boolean isActive() {
        return status == Status.ACTIVE;
    }


    @Override
    public String toString() {
        return "OrderStatus{" +
                "status=" + status +
                '}';
    }

}