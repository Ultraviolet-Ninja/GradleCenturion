package bomb.tools.boot;

import java.util.stream.Stream;

public final class ParallelStreamBootDrive extends StreamBootDrive {
    ParallelStreamBootDrive(){}

    @Override
    Stream<Class<?>> supplyStream() {
        return FxmlBootDrive.getAnnotatedClasses().parallelStream();
    }
}
