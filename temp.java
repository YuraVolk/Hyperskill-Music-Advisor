import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.List;

class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }

    Node create(int value) {
        if (left == null) {
            left = new Node(value);
            return left;
        } else {
            right = new Node(value);
            return right;
        }

    }

    boolean isLeaf() {
        if (right == null && left == null) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Node> extractValues(Node n) {
        List<Node> result = new ArrayList<>();
        if (n.left != null) {
            result.addAll(extractValues(n.left));
        }

        if (n.right != null) {
            result.addAll(extractValues(n.right));
        }

        result.add(n);

        return result;
    }
}

class Main {
    private static boolean checkElement(List<Node> nodes, int testValue) {
        for (Node node : nodes) {
            if (node.value == testValue) {
                return true;
            }
        }
        return false;
    }

    private static int getElement(List<Node> nodes, int testValue) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).value == testValue) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int node = scanner.nextInt();
            int toAdd = scanner.nextInt();
            if (!checkElement(nodes, node)) {
                nodes.add(new Node(node).create(toAdd));
            } else {
                nodes.add(nodes.get(getElement(nodes, node)
                )               .create(toAdd));
            }
        }

        Node root = new Node(0);
        root.create(1);
        Node val2 = root.create(2);
        val2.create(5);


        ArrayList<Integer> values = new ArrayList<>();

        for (Node node : nodes) {
            if (node.isLeaf()) {
                values.add(node.value);
            }
        }
        Collections.sort(values);

        System.out.println(values.size());
        for (Integer num : values) {
            System.out.print(num + " ");
        }
    }
}


