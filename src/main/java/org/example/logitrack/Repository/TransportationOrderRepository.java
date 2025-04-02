package org.example.logitrack.Repository;

import org.example.logitrack.Domain.TransportationOrder;

import java.util.List;

/**
 * Интерфейс репозитория для хранения и получения агрегатов TransportationOrder.
 */
public interface TransportationOrderRepository {
    void save(TransportationOrder order);
    TransportationOrder findById(String id);
    List<TransportationOrder> findAll();
}