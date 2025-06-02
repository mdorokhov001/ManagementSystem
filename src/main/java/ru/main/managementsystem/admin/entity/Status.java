package ru.main.managementsystem.admin.entity;

import javafx.beans.property.*;

public class Status {
    private final IntegerProperty id;
    private final StringProperty name;
    private final BooleanProperty isFinal;

    public Status(int id, String name, boolean isFinal) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.isFinal = new SimpleBooleanProperty(isFinal);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isIsFinal() {
        return isFinal.get();
    }

    public BooleanProperty isFinalProperty() {
        return isFinal;
    }

    public void setIsFinal(boolean isFinal) {
        this.isFinal.set(isFinal);
    }
}
