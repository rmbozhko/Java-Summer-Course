package edu.summer.java;

public class Student {
    private String  name;
    private String  group;
    private int     age;

    public Student(String name, String group, int age) {
        this.name = name;
        this.group = group;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean  equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student that = (Student) o;
        return  that.getAge() == getAge() &&
                that.getGroup().equals(getGroup()) &&
                that.getName().equals(getName());
    }

    @Override
    public int      hashCode() {
        int result = name.hashCode();
        result = 31 * result + group.hashCode();
        result = 31 * result + age;
        return result;
    }
}
