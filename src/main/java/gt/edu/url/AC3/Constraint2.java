package gt.edu.url.AC3;

import java.util.List;

public interface Constraint2 {
    List<Variable> getScope();

    boolean isSatisfiedWith(Assignment assignment);
}
