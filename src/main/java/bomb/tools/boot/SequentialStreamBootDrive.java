package bomb.tools.boot;

import java.util.stream.Stream;

public final class SequentialStreamBootDrive extends StreamBootDrive {
    SequentialStreamBootDrive(){}

    @Override
    protected Stream<Class<?>> supplyStream() {
        return FxmlBootDrive.getAnnotatedClasses().stream();
    }
}
