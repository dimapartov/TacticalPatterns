package org.example.logitrack.Domain;

import java.util.Objects;


public final class DeliveryRoute {

    private final String origin;
    private final String destination;

    public DeliveryRoute(String origin, String destination) {
        if (origin == null || destination == null || origin.isEmpty() || destination.isEmpty()) {
            throw new IllegalArgumentException("Точки отправления и получения не найдены");
        }
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }


    @Override
    public String toString() {
        return "DeliveryRoute{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

}