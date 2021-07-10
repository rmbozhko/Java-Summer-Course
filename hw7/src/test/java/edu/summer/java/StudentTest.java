package edu.summer.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static Student  student;

    @BeforeAll
    static void     init() {
        student = new Student("Test", "test", 42);
    }

    @Test
    void testEqualsReflexiveProperty() {
        Assertions.assertTrue(student.equals(student));
    }

    @Test
    void testEqualsNullProperty() {
        Assertions.assertFalse(student.equals(null));
    }

    @Test
    void testEqualsDifferentClassInstance() {
        Assertions.assertFalse(student.equals(new String()));
    }

    @Test
    void testEqualsSymmetricProperty() {
        Student studentTest = new Student("Test", "test", 42);
        Assertions.assertTrue(student.equals(studentTest));
    }

    @Test
    void testEqualsTransitiveProperty() {
        Student studentTest = new Student("Test", "test", 42);
        Student studentTestReference = studentTest;
        Assertions.assertTrue(student.equals(studentTest));
        Assertions.assertTrue(studentTest.equals(studentTestReference));
        Assertions.assertTrue(studentTestReference.equals(student));
    }

    @Test
    void testEqualsConsistentProperty() {
        Student studentTest = new Student("Test", "test", 42);
        Assertions.assertTrue(studentTest.equals(student));
    }


    @Test
    void testHashCodeSameReferences() {
        Student studentReference = student;
        Assertions.assertEquals(studentReference.hashCode(), student.hashCode());
    }

    @Test
    void testHashCodeSameObjects() {
        Student studentReference = new Student("Test", "test", 42);
        Assertions.assertEquals(studentReference.hashCode(), student.hashCode());
    }

    @Test
    void testHashCodeDifferentObject() {
        Student studentReference = new Student("test", "Test", 42);
        studentReference.setAge(15);
        Assertions.assertNotEquals(studentReference.hashCode(), student.hashCode());
    }
}