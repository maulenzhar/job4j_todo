package ru.job4j.todo.dto;

public class TimeZoneDTO {
    private String id;
    private String displayName;

    public TimeZoneDTO(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
