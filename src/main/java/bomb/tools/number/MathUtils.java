package bomb.tools.number;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public final class MathUtils {
    public static final int HASHING_NUMBER = 5501;

    public static boolean isPerfectSquare(long num) {
        double root = Math.sqrt(num);
        return (root - Math.floor(root)) == 0;
    }

    public static boolean isPrime(long n) {
        if(n % 2 == 0 || n % 3 == 0)
            return n <= 3 && n > 1;

        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }
        return n != 1;
    }

    public static int digitalRoot(long number) {
        long root = 0;

        while (number > 0 || root > 9) {
            if (number == 0) {
                number = root;
                root = 0;
            }

            root += number % 10;
            number /= 10;
        }
        return (int) root;
    }

    public static int digitalRoot(double number) {
        number = Double.parseDouble(String.valueOf(number).replace(".", ""));
        return digitalRoot((long) number);
    }

    public static boolean isAnInteger(double number) {
        return number % 1 == 0.0;
    }

    public static double roundToNPlaces(double number, int places) {
        double factor = pow(10.0, places);
        return round(number * factor) / factor;
    }

    public static int negativeSafeModulo(int number, final int modulus) {
        while(number < 0) number += modulus;
        return number % modulus;
    }
}
