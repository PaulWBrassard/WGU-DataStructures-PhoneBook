package phonebook;

import java.util.Objects;

/**
 * Contact describes a contact you would find in a phone book. It implements
 * Comparable to be usable in data structures that require it.
 *
 * @author Paul Brassard
 */
public class Contact implements Comparable {

    private String name, phone, email;

    //PB - Overloaded constructor for a Contact with only a name.
    public Contact(String name) {
        this(name, null, null);
    }

    //PB - Primary constructor setting all fields.
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    //PB - Return contact name.
    public String getName() {
        return name;
    }

    //PB - Set contact name.
    public void setName(String name) {
        this.name = name;
    }

    //PB - Get contact email.
    public String getEmail() {
        return email;
    }

    //PB - Set contact email.
    public void setEmail(String email) {
        this.email = email;
    }

    //PB - Get contact phone number.
    public String getPhone() {
        return phone;
    }

    //PB - Set contact phone number.
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //PB - Returns a readable String for the contact.
    @Override
    public String toString() {
        return String.format("Name: %s\nPhone: %s\nEmail: %s\n", name, phone, email);
    }

    //PB - Contacts are considered equal if the name is the same.
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contact)) {
            return false;
        }
        return this.name.equals(((Contact) o).name);
    }

    //PB - Returns an int hash code based on the name.
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    //PB - Returns an int to compare a Contact to another based on names.
    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Contact) o).name);
    }
}
