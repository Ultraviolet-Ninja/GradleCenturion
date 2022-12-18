package bomb;

import bomb.tools.filter.Regex;

public class TestingArea {
    public static void main(String[] args) {
        Regex labelFilter = new Regex("\"([^\"]*)\",?");
        Regex frequencyFilter = new Regex("frequencies\\.put\\(\"([^\"]+)\", (\\d\\.\\d{1,3})\\);");
        Regex whoMapFilter = new Regex("stepTwoMap\\.put\\(\"([^\"]+)\", \"([^\"]+)\"\\);");

        method(Widget.class);
    }

    public static void method(Class<?> cls) {
        System.out.println(cls);
        var subClasses = cls.getPermittedSubclasses();
        if (subClasses != null) {
            for (Class<?> cl : subClasses) {
                method(cl);
            }
        }
    }
}