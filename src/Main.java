public class Main {
    public static void main(String[] args) {

        class Apartment {
            int flor;
            int number;

            public Apartment(int flor, int number) {
                this.flor = flor;
                this.number = number;
            }
        }

        class Address {
            String street;
            String city;
            Apartment apartment;

            public Address(String street, String city, Apartment apartment) {
                this.street = street;
                this.city = city;
                this.apartment = apartment;
            }
        }

        class Person {
            String name;
            int age;
            Address address;
            int id;

            public Person(String name, int age, Address address, int id) {
                this.name = name;
                this.age = age;
                this.address = address;
                this.id = id;
            }
        }

        Person oleh = new Person("Oleh", 23, new Address("Olgi", "Lviv", new Apartment(9, 363)), 1);
        CustomSerializer customSerializer = new CustomSerializer();
        customSerializer.serialize(oleh, SereializerType.JSON);
    }
}