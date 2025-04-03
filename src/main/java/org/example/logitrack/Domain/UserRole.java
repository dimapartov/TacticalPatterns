package org.example.logitrack.Domain;


public enum UserRole {
    CLIENT,
    PROVIDER;

    public UserRole getOpposite() {
        return this == CLIENT ? PROVIDER : CLIENT;
    }
}