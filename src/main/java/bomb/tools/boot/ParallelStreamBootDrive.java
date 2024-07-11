package bomb.tools.boot;

import java.util.stream.Stream;

public final class ParallelStreamBootDrive extends StreamBootDrive {
    ParallelStreamBootDrive(){}

    @Override
    protected Stream<Class<?>> supplyStream() {
        return FxmlBootDrive.getAnnotatedClasses().parallelStream();
    }
}
