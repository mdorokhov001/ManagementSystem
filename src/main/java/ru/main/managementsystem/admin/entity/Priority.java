package ru.main.managementsystem.admin.entity;

public enum Priority {
    HIGH(1, "Высокий"),
    MEDIUM(2, "Средний"),
    LOW(3, "Низкий");

    private final int code;
    private final String displayName;

    Priority(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Получение Priority по коду (числу из БД)
    public static Priority fromCode(int code) {
        for (Priority p : values()) {
            if (p.code == code) {
                return p;
            }
        }
        return null; // или throw new IllegalArgumentException("Unknown priority code: " + code);
    }

    // Получение Priority по строке (например, из UI)
    public static Priority fromDisplayName(String displayName) {
        for (Priority p : values()) {
            if (p.displayName.equalsIgnoreCase(displayName)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
