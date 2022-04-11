package Main;

import java.util.ArrayList;
import java.util.List;

public class Ghost {
    private int size = 0;
    private final List<Node> tree;

    public Ghost() {
        tree = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public void add(Node node) {
        tree.add(node);
        size++;
    }

    public void add(Ghost ghost) {
        tree.addAll(ghost.tree);
        size += ghost.size;
    }

    public List<Node> getTree() {
        return tree;
    }
}
