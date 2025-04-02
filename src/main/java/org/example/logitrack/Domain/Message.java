package org.example.logitrack.Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Сущность «Сообщение» в заявке.
 * Содержит информацию о содержимом, отправителе, вложениях и статусе подтверждения.
 * Инвариант: подтверждение сообщения доступно только противоположной стороне.
 * Вложения добавляются только на этапе создания сообщения.
 */
public class Message {
    private final String id;
    private final UserRole sender;
    private final String content;
    private final List<Attachment> attachments;
    private boolean isConfirmed;

    /**
     * Создание сообщения с вложениями.
     * @param id Идентификатор сообщения.
     * @param sender Роль отправителя.
     * @param content Содержимое сообщения.
     * @param attachments Список вложений (если их нет, передать пустой список).
     */
    public Message(String id, UserRole sender, String content, List<Attachment> attachments) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Message id cannot be null or empty");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (attachments == null) {
            throw new IllegalArgumentException("Attachments list cannot be null (can be empty if no attachments)");
        }
        this.id = id;
        this.sender = sender;
        this.content = content;
        // Инвариант: вложения добавляются только при создании сообщения.
        this.attachments = new ArrayList<>(attachments);
        this.isConfirmed = false;
    }

    public String getId() {
        return id;
    }

    public UserRole getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public List<Attachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    /**
     * Подтверждение сообщения. Метод проверяет, что подтверждение происходит
     * только от противоположной стороны (отличной от отправителя).
     * @param confirmer Роль, пытающаяся подтвердить сообщение.
     */
    public void confirmMessage(UserRole confirmer) {
        if (confirmer == null) {
            throw new IllegalArgumentException("Confirmer role cannot be null");
        }
        if (confirmer != sender.getOpposite()) {
            throw new IllegalStateException("Only the opposite party can confirm the message");
        }
        if (isConfirmed) {
            throw new IllegalStateException("Message is already confirmed");
        }
        this.isConfirmed = true;
        System.out.println("Message " + id + " confirmed by " + confirmer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Message{id='" + id + "', sender=" + sender + ", content='" + content + "', attachments=" + attachments + ", confirmed=" + isConfirmed + "}";
    }
}