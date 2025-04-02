package org.example.logitrack.Service;

import org.example.logitrack.Domain.Message;
import org.example.logitrack.Domain.TransportationOrder;
import org.example.logitrack.Domain.UserRole;

import java.util.HashMap;
import java.util.Map;


/**
 * Доменный сервис для обнаружения мошенничества.
 * Эмуляция проверки: если от одной стороны поступает более 3 сообщений,
 * то считается подозрительной активностью.
 */
public class FraudDetectionService {

    /**
     * Проверяет заявку на подозрительную активность.
     *
     * @return true, если подозрение на мошенничество обнаружено, иначе false.
     */
    public boolean checkForFraud(TransportationOrder order) {
        Map<UserRole, Integer> messageCount = new HashMap<>();

        // Для упрощения считаем все сообщения заявки.
        for (Message msg : order.getMessages()) {
            messageCount.put(msg.getSender(), messageCount.getOrDefault(msg.getSender(), 0) + 1);
        }

        for (Map.Entry<UserRole, Integer> entry : messageCount.entrySet()) {
            if (entry.getValue() > 3) {
                System.out.println("Fraud detection alert: More than 3 messages from " + entry.getKey() + " in order " + order.getId());
                return true;
            }
        }
        System.out.println("No fraud detected for order " + order.getId());
        return false;
    }

}