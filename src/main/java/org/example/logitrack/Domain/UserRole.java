package org.example.logitrack.Domain;

/**
 * Enum для представления ролей пользователей.
 * Используется для определения, какая сторона (CLIENT или PROVIDER)
 * отправила сообщение и кто может его подтвердить.
 */
public enum UserRole {
    CLIENT,
    PROVIDER;

    /**
     * Возвращает противоположную роль.
     */
    public UserRole getOpposite() {
        return this == CLIENT ? PROVIDER : CLIENT;
    }
}