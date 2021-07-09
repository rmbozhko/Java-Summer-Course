package edu.summer.java.model;

/**
 * RecordBuilder contains base methods for various types of RecordBuilders.
 * @see RecordModelBuilder
 * @author Roman Bozhko
 * @version 1.0
 */
public interface RecordBuilder {
    void    setName(String name);
    void    setSurname(String surname);
    void    setFatherName(String fatherName);
    void    setNickname(String nickname);
}
