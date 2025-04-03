package org.example.logitrack.Service;

import org.example.logitrack.Domain.Message;
import org.example.logitrack.Domain.TransportationOrder;
import org.example.logitrack.Domain.UserRole;

import java.util.HashMap;
import java.util.Map;



public class FraudDetectionService {


    public boolean checkForFraud(TransportationOrder order) {
        Map<UserRole, Integer> messageCount = new HashMap<>();

        for (Message msg : order.getMessages()) {
            messageCount.put(msg.getSender(), messageCount.getOrDefault(msg.getSender(), 0) + 1);
        }

        for (Map.Entry<UserRole, Integer> entry : messageCount.entrySet()) {
            if (entry.getValue() > 3) {
                System.out.println("Обнаружено мошенничество: более трех сообщений от " + entry.getKey() + " в заявке " + order.getId());
                return true;
            }
        }
        System.out.println("Мошенничество не обнаружено. Заявка: " + order.getId());
        return false;
    }

}