package org.example.logitrack.Repository;

import org.example.logitrack.Domain.TransportationOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * In-memory реализация репозитория для агрегатов TransportationOrder.
 */
public class TransportationOrderRepository {

    private final Map<String, TransportationOrder> storage = new HashMap<>();

    public void save(TransportationOrder order) {
        storage.put(order.getId(), order);
        System.out.println("Order saved: " + order.getId());
    }

    public TransportationOrder findById(String id) {
        return storage.get(id);
    }

    public List<TransportationOrder> findAll() {
        return new ArrayList<>(storage.values());
    }

}