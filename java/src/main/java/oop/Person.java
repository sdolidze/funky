package oop;

/**
 * Created by sandro on 1/4/15.
 */
public class Person {
    private final String firstName;
    private final String lastName;
    private int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        return new Person(firstName, lastName, age);
    }

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        return new Person(firstName, lastName, age);
    }

    public int getAge() {
        return age;
    }

    public Person setAge(int age) {
        return new Person(firstName, lastName, age);
    }

    public static void main(String[] args) {
        Atom<Person> person = new Atom<>(new Person("sandro", "dolidze", 21));
        person.swap(p -> p.setFirstName("sandro").setAge(15));
        System.out.println(person.get().getFirstName());
    }
}
