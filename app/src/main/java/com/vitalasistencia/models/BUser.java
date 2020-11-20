package com.vitalasistencia.models;

public class BUser {
    private String name;

    public BUser() {
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.startsWith("a")) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }
}
