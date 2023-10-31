package bomb.tools.boot;

import bomb.tools.pattern.observer.ResetObserver;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public final class ForkJoinBootDrive implements FxmlBootDrive {
    ForkJoinBootDrive(){}

    @Override
    public SequencedMap<String, Region> createFXMLMap(ResetObserver resetObserver) {
        var classList = FxmlBootDrive.getAnnotatedClasses();
        try (ForkJoinPool pool = new ForkJoinPool()) {
            var generatorTask = new RegionGenerator(classList, resetObserver);

            return pool.invoke(generatorTask);
        }
    }

    private static class RegionGenerator extends RecursiveTask<SequencedMap<String, Region>> {
        private final List<Class<?>> classList;
        private final ResetObserver observer;

        private RegionGenerator(List<Class<?>> classList, ResetObserver observer) {
            this.classList = classList;
            this.observer = observer;
        }

        @Override
        protected @NotNull SequencedMap<String, Region> compute() {
            if (classList.size() != 1) {
                var splitList = splitList(classList);
                var taskOne = new RegionGenerator(splitList.getFirst(), observer);
                var taskTwo = new RegionGenerator(splitList.getLast(), observer);

                taskOne.fork();
                taskTwo.fork();

                var output = new LinkedHashMap<>(taskOne.join());
                output.putAll(taskTwo.join());
                return output;
            }
            var pair = FxmlBootDrive.mapClassToRegion(classList.getFirst(), observer);
            return new LinkedHashMap<>(Map.of(pair.getValue0(), pair.getValue1()));
        }

        private static List<List<Class<?>>> splitList(List<Class<?>> list){
            int split = list.size() >> 1;
            List<List<Class<?>>> output = new ArrayList<>();
            output.add(new ArrayList<>(list.subList(0, split)));
            output.add(new ArrayList<>(list.subList(split, list.size())));
            return output;
        }
    }
}
