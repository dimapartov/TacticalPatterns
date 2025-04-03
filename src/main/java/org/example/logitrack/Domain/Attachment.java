package org.example.logitrack.Domain;

import java.util.Objects;


public class Attachment {

    private final String id;
    private final String fileName;

    public Attachment(String id, String fileName) {
        if (id == null || fileName == null || id.isEmpty() || fileName.isEmpty()) {
            throw new IllegalArgumentException("Вложение не найдено");
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
    public String toString() {
        return "Attachment{" +
                "id='" + id + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

}