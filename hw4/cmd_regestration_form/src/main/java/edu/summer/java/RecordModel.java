package edu.summer.java;

/**
 * Class RecordModel contains fields used to represent regular online platform user.
 * @author Roman Bozhko
 * @version 1.0
 */
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return surname + ' ' + name.charAt(0) + ".\n" + nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordModel that = (RecordModel) o;

        if (!getName().equals(that.getName())) return false;
        if (!getSurname().equals(that.getSurname())) return false;
        if (!getFatherName().equals(that.getFatherName())) return false;
        return getNickname().equals(that.getNickname());
    }

    @Override
    public int hashCode() {
        return getNickname().hashCode();
    }
}
