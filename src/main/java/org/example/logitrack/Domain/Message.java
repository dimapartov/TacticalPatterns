package org.example.logitrack.Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;



public class Message {

    private final String id;
    private final UserRole sender;
    private final String content;
    private final List<Attachment> attachments;
    private boolean isConfirmed;


    public Message(String id, UserRole sender, String content, List<Attachment> attachments) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Сообщение не найдено");
        }
        if (sender == null) {
            throw new IllegalArgumentException("Отправитель не найден");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Сообщение не может быть пустым");
        }
        if (attachments == null) {
            throw new IllegalArgumentException("Вложения не найдены");
        }
        this.id = id;
        this.sender = sender;
        this.content = content;
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


    public void confirmMessage(UserRole confirmer) {
        if (confirmer == null) {
            throw new IllegalArgumentException("Роль не найдена");
        }
        if (confirmer != sender.getOpposite()) {
            throw new IllegalStateException("Только другая сторона может подтвердить сообщение");
        }
        if (isConfirmed) {
            throw new IllegalStateException("Сообщение уже подтверждено");
        }
        this.isConfirmed = true;
        System.out.println("Сообщение " + id + " подтверждено " + confirmer);
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
        return "Message{" +
                "id='" + id + '\'' +
                ", sender=" + sender +
                ", content='" + content + '\'' +
                ", attachments=" + attachments +
                ", isConfirmed=" + isConfirmed +
                '}';
    }

}