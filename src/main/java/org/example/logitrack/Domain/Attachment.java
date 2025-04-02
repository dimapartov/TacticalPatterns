package org.example.logitrack.Domain;

import java.util.Objects;


/**
 * Сущность «Вложение».
 * Вложение может быть добавлено только вместе с новым сообщением.
 */
public class Attachment {

    private final String id;
    private final String fileName;

    public Attachment(String id, String fileName) {
        if (id == null || fileName == null || id.isEmpty() || fileName.isEmpty()) {
            throw new IllegalArgumentException("Attachment id and fileName cannot be null or empty");
        }
        this.id = id;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        Attachment that = (Attachment) o;
        return id.equals(that.id) && fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName);
    }

    @Override
    public String toString() {
        return "Attachment{id='" + id + "', fileName='" + fileName + "'}";
    }

}