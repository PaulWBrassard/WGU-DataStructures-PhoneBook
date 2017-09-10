package phonebook;

/**
 * HashTable is a data structure using Generics for reusability. Nodes are
 * placed into thirteen buckets based on their keys being hashed, this is done
 * with a linked list implementation.
 *
 * @author Paul Brassard
 * @param <K> for Key
 * @param <V> for Value
 */
public class HashTable<K, V> {

    //PB - Node is a linked list implementation to be used for the hash table.
    private class Node<K, V> {

        Node next;
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    Node[] table;

    //PB - Constructs a HashTable with 13 buckets.
    public HashTable() {
        table = new Node[13];
    }

    //PB - Adds a value to the hash table by hashing the key.
    public void add(K key, V value) {
        //PB - Get the index by hashing the key.
        int index = hash(key);
        Node addition = new Node(key, value);
        Node current = table[index];
        //PB - Make the old root node next to this new one and connect new one to table.
        addition.next = current;
        table[index] = addition;
    }

    //PB - Hashes a key ensuring it fits within the table with 13 buckets.
    private int hash(K key) {
        int result = key.hashCode() % 13;
        if (result < 0) {
            //PB - Ensure result is positive.
            result *= -1;
        }
        return result;
    }

    //PB - Finds a value in the hash table by key. Returns null if not found.
    public V getValue(K key) {
        int index = hash(key);
        Node current = table[index];
        //PB - Search through nodes for matching key.
        while (current != null) {
            if (current.key.equals(key)) {
                break;
            }
            current = current.next;
        }
        //PB - Null return shows the value wasn't found.
        if (current == null) {
            return null;
        }
        return ((V) current.value);
    }

    //PB - Removes a value from the hash table by key. Returns null if not found.
    public V remove(K key) {
        int index = hash(key);
        Node current = table[index];
        //PB - Search through nodes for matching key.
        while (current != null) {
            if (current.key.equals(key)) {
                break;
            }
            current = current.next;
        }
        //PB - Null return shows the value wasn't found.
        if (current == null) {
            return null;
        }
        //PB - Removing links to the node effectively removes it.
        table[index] = current.next;
        return ((V) current.value);
    }
}
