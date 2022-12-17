package bomb;

import bomb.initialization.Puzzle;
import bomb.initialization.PuzzleSupplier;
import bomb.tools.filter.Regex;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class TestingArea {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

        for (var cls : PuzzleSupplier.provideClassList()) {
            System.out.println(cls);
            System.out.println(cls.isAnnotationPresent(Puzzle.class));
        }

        // Get the ClassLoader for the current thread
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//
//// Get a list of all the class files in the package and its subpackages
//        Enumeration<URL> resources = classLoader.getResources("bomb/modules");
//        while (resources.hasMoreElements()) {
//            URL url = resources.nextElement();
//            // Load the classes from the URL and check if they are annotated with the given annotation
//            URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url });
//            File dir = new File(url.getFile());
//            for (File file : dir.listFiles()) {
//                if (file.getName().endsWith(".class")) {
//                    String className = file.getName().substring(0, file.getName().length() - 6);
//                    Class<?> cls = urlClassLoader.loadClass("my.package." + className);
//                    if (cls.isAnnotationPresent(Puzzle.class)) {
//                        // cls is annotated with the MyAnnotation annotation
//                    }
//                }
//            }
//        }
    }
}