package bomb.tools.boot;

import bomb.Widget;
import bomb.annotation.DisplayComponent;
import bomb.modules.ab.blind.alley.BlindAlley;
import bomb.modules.ab.blind.alley.BlindAlleyController;
import bomb.modules.s.souvenir.Souvenir;
import bomb.modules.s.souvenir.SouvenirController;
import bomb.modules.t.translated.TranslationComponent;
import bomb.tools.note.NoteController;
import bomb.tools.pattern.facade.FacadeFX;
import bomb.tools.pattern.observer.BlindAlleyPaneObserver;
import bomb.tools.pattern.observer.ObserverHub;
import bomb.tools.pattern.observer.ResetObserver;
import bomb.tools.pattern.observer.SouvenirPaneObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import org.javatuples.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.SequencedMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.BLIND_ALLEY_PANE;
import static bomb.tools.pattern.observer.ObserverHub.ObserverIndex.SOUVENIR_PANE;
import static java.util.Arrays.asList;

/**
 * FxmlBootDrive uses the Strategy Pattern to perform the part of the boot sequence with the highest bottleneck in performance.
 * The purpose for this set of classes is to have a way to quickly switch between methodologies for booting the program
 * under different operating systems scenarios. As the number of FXML files for the project increases,
 * maintaining a short boot time for this program will more become critical.
 * <p>
 * The standard way uses a stream. This stream is sequential on Linux machines and parallel on Windows,
 * thus using the thread pool initialized by Java. This means that performance is dependent on how many pooled threads
 * can be created by the JVM.
 * <p>
 * The new way uses Java 21's virtual threads so that we can see if there are any promising results in boot time.
 * As of Oct 13, 2023 on Windows, the performance is comparable to the standard drive, perhaps slightly faster; however,
 * it's not reliable. I.e. some runs will boot with no problem, and other runs will abruptly pause while loading FXML files.
 * Reason is unknown, but more testing is required on other machines.
 */
public sealed interface FxmlBootDrive permits AsyncBootDrive, ForkJoinBootDrive, StreamBootDrive,
        VirtualThreadFxmlBootDrive {
    String WIDGET_FILE = extractAssociatedFile(Widget.class);
    String SOUVENIR_FILE = extractAssociatedFile(Souvenir.class);
    String BLIND_ALLEY_FILE = extractAssociatedFile(BlindAlley.class);
    List<Class<?>> INITIAL_BOOT_CLASSES = List.of(
            Widget.class, NoteController.class, TranslationComponent.class
    );

    Logger LOG = LoggerFactory.getLogger(FxmlBootDrive.class);

    SequencedMap<String, Region> createFXMLMap(ResetObserver resetObserver);

    default SequencedMap<String, CompletableFuture<Region>> createFXMLMapAsync(ResetObserver resetObserver) {
        throw new UnsupportedOperationException();
    }

    @Contract(" -> new")
    static @NotNull FxmlBootDrive createParallelStreamDrive() {
        return new ParallelStreamBootDrive();
    }

    @Contract(" -> new")
    static @NotNull FxmlBootDrive createVirtualDrive() {
        return new VirtualThreadFxmlBootDrive();
    }

    @Contract(" -> new")
    static @NotNull FxmlBootDrive createSequentialStreamDrive() {
        return new SequentialStreamBootDrive();
    }

    @Contract(value = " -> new", pure = true)
    static @NotNull FxmlBootDrive createForkJoinDrive() {
        return new ForkJoinBootDrive();
    }

    @Contract(value = " -> new", pure = true)
    static @NotNull FxmlBootDrive createAsyncBootDrive() {
        return new AsyncBootDrive();
    }

    static @NotNull List<Class<?>> getAnnotatedClasses() {
        var annotatedClasses = new ArrayList<>(INITIAL_BOOT_CLASSES);
        var queue = new ArrayDeque<>(asList(Widget.class.getPermittedSubclasses()));

        Class<?> temp;
        while ((temp = queue.poll()) != null) {
            Class<?>[] subclasses = temp.getPermittedSubclasses();
            if (subclasses != null) {
                queue.addAll(asList(subclasses));
            }

            if (temp.isAnnotationPresent(DisplayComponent.class)) {
                annotatedClasses.add(temp);
            }
        }
        return annotatedClasses;
    }

    static @NotNull Pair<String, Region> mapClassToRegion(@NotNull Class<?> clazz, ResetObserver resetObserver) {
        return mapClassToType(clazz, resetObserver, FxmlBootDrive::loadSingleRegion);
    }

    static @NotNull <T> Pair<String, T> mapClassToType(@NotNull Class<?> clazz, ResetObserver resetObserver,
                                                       BiFunction<FXMLLoader, ResetObserver, T> regionFunction)
            throws IllegalArgumentException, IllegalStateException {
        DisplayComponent annotation = clazz.getAnnotation(DisplayComponent.class);
        var resource = clazz.getResource(annotation.resource());
        var buttonLinkerName = annotation.buttonLinkerName();

        if (resource == null) {
            LOG.error("{} has a null resource. Make sure the FXML file is in the same package", buttonLinkerName);
            throw new IllegalStateException();
        }

        return new Pair<>(
                buttonLinkerName,
                regionFunction.apply(new FXMLLoader(resource), resetObserver)
        );
    }

    static Region loadSingleRegion(FXMLLoader loader, ResetObserver resetObserver)
            throws IllegalArgumentException {
        Region output = FacadeFX.load(loader);
        String location = loader.getLocation().toString();

        if (!location.endsWith(WIDGET_FILE)) resetObserver.addController(loader);

        if (location.endsWith(SOUVENIR_FILE)) loadSouvenirController(loader.getController());
        else if (location.endsWith(BLIND_ALLEY_FILE)) loadBlindAlleyController(loader.getController());
        return output;
    }

    private static void loadBlindAlleyController(BlindAlleyController controller) {
        ObserverHub.addObserver(BLIND_ALLEY_PANE, new BlindAlleyPaneObserver(controller));
    }

    private static void loadSouvenirController(SouvenirController controller) {
        ObserverHub.addObserver(SOUVENIR_PANE, new SouvenirPaneObserver(controller));
    }

    private static String extractAssociatedFile(Class<?> clazz) {
        return clazz.getAnnotation(DisplayComponent.class)
                .resource();
    }
}
