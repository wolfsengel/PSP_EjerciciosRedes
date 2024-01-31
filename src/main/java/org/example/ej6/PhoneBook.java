package org.example.ej6;

public class PhoneBook {
    private Contact[] contacts;

    public PhoneBook() {
        this.contacts = new Contact[0];
    }

    public void addContact(Contact contact) {
        Contact[] newContacts = new Contact[this.contacts.length + 1];
        System.arraycopy(this.contacts, 0, newContacts, 0, this.contacts.length);
        newContacts[this.contacts.length] = contact;
        this.contacts = newContacts;
    }

    public Contact[] getContacts() {
        return this.contacts;
    }

    public Contact getContact(String name) {
        for (Contact contact : this.contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

    public void removeContact(String name) {
        Contact[] newContacts = new Contact[this.contacts.length - 1];
        int j = 0;
        for (Contact contact : this.contacts) {
            if (!contact.getName().equals(name)) {
                newContacts[j] = contact;
                j++;
            }
        }
        this.contacts = newContacts;
    }
}
