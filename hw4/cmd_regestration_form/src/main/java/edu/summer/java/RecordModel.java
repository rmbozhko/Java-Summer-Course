package edu.summer.java;

public class RecordModel {
    private final String    name;
    private final String    surname;
    private final String    fatherName;
    private final String    nickname;

    public RecordModel(String name, String surname, String fatherName, String nickname) {
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ПІБ: " + surname + ' ' + name.charAt(0) + ".\n" +
                "Никнейм: " + nickname;
    }
}
