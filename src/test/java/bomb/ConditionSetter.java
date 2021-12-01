package bomb;

@FunctionalInterface
public interface ConditionSetter {
    ConditionSetter EMPTY_SETTER = () -> {};

    void setCondition();
}
