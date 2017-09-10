package phonebook;

/**
 * BinaryTree is a data structure using Generics for reusability. There is a
 * root that is the first node, each node may have at most two children, hence
 * binary tree.
 *
 * @author Paul Brassard
 * @param <V> Type of value the tree holds that extends Comparable
 */
public class BinaryTree<V extends Comparable> {

    //PB - Nodes are places in the BinaryTree that may have a parent and children.
    private class Node {

        V value;
        Node parent, left, right;

        Node(V value) {
            this.value = value;
        }

        //PB - Return how many children the node has.
        int childCount() {
            int count = 0;
            if (left != null) {
                count++;
            }
            if (right != null) {
                count++;
            }
            return count;
        }
    }

    Node root = null;

    //PB - add a new addition to the tree.
    public void add(V value) {
        Node addition = new Node(value);
        if (root == null) {
            root = addition;
        } else {
            //PB - call recursive add starting with root
            add(root, addition);
        }
    }

    //PB - Recursive add to place new node underneath the appropriate parent.
    private void add(Node current, Node addition) {
        //PB - Current node cannot be null as this method takes action on the node.
        if (current == null) {
            throw new IllegalArgumentException("Current node cannot be null");
        }
        //PB - go left if the addition value is of less value than parent.
        if (addition.value.compareTo(current.value) < 0) {
            //PB - Addition becomes left child if there is none, otherwise recurse left.
            if (current.left == null) {
                current.left = addition;
                addition.parent = current;
            } else {
                add(current.left, addition);
            }
        }
        //PB - go right if the addition value is of more value than parent.
        if (addition.value.compareTo(current.value) > 0) {
            //PB - Addition becomes right child if there is none, otherwise recurse right.
            if (current.right == null) {
                current.right = addition;
                addition.parent = current;
            } else {
                add(current.right, addition);
            }
        }
    }

    //PB - Find a value in the tree. Returns null if not found.
    public V getValue(V value) {
        //PB - Call recursive getValue passing the root as the start
        Node match = find(root, value);
        if (match == null) {
            return null;
        }
        return match.value;
    }

    //PB - Recursive getValue to get a node by name. Returns null if not found.
    private Node find(Node current, V value) {
        //PB - Node being null proves there is no match.
        if (current == null) {
            return null;
        }
        //PB - Check if the current node is what we are looking for.
        if (current.value.equals(value)) {
            return (current);
        }
        //PB - Go left if the search name is of less value than current node name.
        if (value.compareTo(current.value) < 0) {
            return find(current.left, value);
        }
        //PB - Go right if the search name is of more value than current node name.
        return find(current.right, value);
    }

    //PB - Remove a value from the tree and return the value upon removal.
    public V remove(V value) {
        Node target = find(root, value);
        if (target == null) {
            return null;
        }
        V match = target.value;
        switch (target.childCount()) {
            //PB - No children, so remove the correct child link from the parent.
            case 0:
                if (target.equals(root)) {
                    root = null;
                } else if (target.value.compareTo(target.parent.value) < 0) {
                    target.parent.left = null;
                } else {
                    target.parent.right = null;
                }
                break;
            //PB - One child, so replace target with its child.
            case 1:
                //PB - Find correct child.
                Node child = (target.left != null) ? target.left : target.right;
                if (target.equals(root)) {
                    root = child;
                } else {
                    //PB - Child accepts grandparent as new parent.
                    child.parent = target.parent;
                    //PB - Grandparent adopts.
                    if (target.value.compareTo(target.parent.value) < 0) {
                        target.parent.left = child;
                    } else {
                        target.parent.right = child;
                    }
                }
                break;
            //PB - Two children, so replace target value with left subtree rightmost descendant.
            case 2:
                Node replacement = target.left;
                while (replacement.right != null) {
                    replacement = replacement.right;
                }
                //PB - Remove link from old parent.
                replacement.parent.right = null;
                //PB - Replacing only the value keeps correct ordering and links.
                target.value = replacement.value;
                break;
        }
        return match;
    }
}
