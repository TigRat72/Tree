package Main;

import org.jetbrains.annotations.NotNull;
import java.lang.*;
import java.util.Objects;

public class Node {
    private final String A;
    private final String B;
    private final String C;

    public Node(@NotNull String A, @NotNull String B, @NotNull String C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    public String toString() {
        return "\"" + A + "\";\"" + B + "\";\"" + C + "\"";
    }
    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(A, node.A) && Objects.equals(B, node.B) && Objects.equals(C, node.C);
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B, C);
    }
}
