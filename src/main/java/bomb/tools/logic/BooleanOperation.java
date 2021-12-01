package bomb.tools.logic;

@FunctionalInterface
public interface BooleanOperation {
    boolean test(boolean bitOne, boolean bitTwo);
}
