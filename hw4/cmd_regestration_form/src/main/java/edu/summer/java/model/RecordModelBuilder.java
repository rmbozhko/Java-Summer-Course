package edu.summer.java.model;

/**
 * Class RecordModelBuilder implements a Builder pattern for RecordModel class.
 * @see RecordModel
 * @author Roman Bozhko
 * @version 1.0
 */
public class RecordModelBuilder implements RecordBuilder {
    private String name;
    private String surname;
    private String fatherName;
    private String nickname;

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public RecordModel getRecord() {
        return new RecordModel(name, surname, fatherName, nickname);
    }
}
