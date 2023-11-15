package com.example.erronka1.ui;

import java.util.Map;

public class CustomData {
    private long id;
    private Map<String, Object> fields;

    public CustomData(long id, Map<String, Object> fields) {
        this.id = id;
        this.fields = fields;
    }

    public long getId() {
        return id;
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}