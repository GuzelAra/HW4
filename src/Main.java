import java.util.ArrayList;

public class BinTree {
    Node root;


    public void print() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        System.out.println("Tree elements:");
        traverseInOrder(root);
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node.value + " " + node.color);
            traverseInOrder(node.right);
        }
    }

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
            root.color = Color.BLACK;
            return;
        }
        addNode(root, value);
    }
    private void addNode(Node node, int value) {
        if (value < node.value) {
            if (node.left == null) {
                node.left = new Node(value);
                node.left.parent = node;
                balanceAfterInsert(node.left);
            } else {
                addNode(node.left, value);
            }
        } else if (value > node.value) {
            if (node.right == null) {
                node.right = new Node(value);
                node.right.parent = node;
                balanceAfterInsert(node.right);
            } else {
                addNode(node.right, value);
            }
        }
    }

    private void balanceAfterInsert(Node node) {
        while (node != null && node != root && node.parent.color == Color.RED) {
            if (getParent(node) == getGrandparent(node).left) {
                Node uncle = getGrandparent(node).right;
                if (uncle != null && uncle.color == Color.RED) {
                    getParent(node).color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    getGrandparent(node).color = Color.RED;
                    node = getGrandparent(node);
                } else {
                    if (node == getParent(node).right) {
                        node = getParent(node);
                        rotateLeft(node);
                    }
                    getParent(node).color = Color.BLACK;
                    getGrandparent(node).color = Color.RED;
                    rotateRight(getGrandparent(node));
                }
            } else {
                Node uncle = getGrandparent(node).left;
                if (uncle != null && uncle.color == Color.RED) {
                    getParent(node).color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    getGrandparent(node).color = Color.RED;
                    node = getGrandparent(node);
                } else {
                    if (node == getParent(node).left) {
                        node = getParent(node);
                        rotateRight(node);
                    }
                    getParent(node).color = Color.BLACK;
                    getGrandparent(node).color = Color.RED;
                    rotateLeft(getGrandparent(node));
                }
            }
        }
        root.color = Color.BLACK;
    }

    private Node getParent(Node node) {
        return node != null ? node.parent : null;
    }

    private Node getGrandparent(Node node) {
        return getParent(getParent(node));
    }

    private void rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        if (temp.left != null) {
            temp.left.parent = node;
        }
        temp.parent = node.parent;
        if (node.parent == null) {
            root = temp;
        } else if (node == node.parent.left) {
            node.parent.left = temp;
        } else {
            node.parent.right = temp;
        }
        temp.left = node;
        node.parent = temp;
    }

    private void rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        if (temp.right != null) {
            temp.right.parent = node;
        }
        temp.parent = node.parent;
        if (node.parent == null) {
            root = temp;
        } else if (node == node.parent.left) {
            node.parent.left = temp;
        } else {
            node.parent.right = temp;
        }
        temp.right = node;
        node.parent = temp;
    }

    private class Node {
        int value;
        Node left;
        Node right;
        Node parent;
        Color color;

        Node(int value) {
            this.value = value;
            this.color = Color.RED;
        }
    }

    enum Color {
        RED, BLACK
    }
}