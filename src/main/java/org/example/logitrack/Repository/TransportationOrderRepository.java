package org.example.logitrack.Repository;

import org.example.logitrack.Domain.TransportationOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransportationOrderRepository {

    private final Map<String, TransportationOrder> storage = new HashMap<>();

    public void save(TransportationOrder order) {
        storage.put(order.getId(), order);
        System.out.println("Заявка сохранена: " + order.getId());
    }

    public TransportationOrder findById(String id) {
        return storage.get(id);
    }

    public List<TransportationOrder> findAll() {
        return new ArrayList<>(storage.values());
    }

}