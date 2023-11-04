package bomb.tools.number;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public final class MathUtils {
    public static final int HASHING_NUMBER = 5501;

    public static boolean isPerfectSquare(long num) {
        double root = Math.sqrt(num);
        return root - Math.floor(root) == 0;
    }

    public static boolean isPrime(long num) {
        if(num % 2 == 0 || num % 3 == 0)
            return num <= 3 && num > 1;

        for (long i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return num != 1;
    }

    public static int digitalRoot(long number) {
        long root = 0;
        long temp = number;

        while (temp > 0 || root > 9) {
            if (temp == 0) {
                temp = root;
                root = 0;
            }

            root += temp % 10;
            temp /= 10;
        }
        return (int) root;
    }

    public static int digitalRoot(double number) {
        double parsedDouble = Double.parseDouble(String.valueOf(number).replace(".", ""));
        return digitalRoot((long) parsedDouble);
    }

    public static boolean isAnInteger(double number) {
        return number % 1 == 0.0;
    }

    public static double roundToNPlaces(double number, int places) {
        double factor = pow(10.0, places);
        return round(number * factor) / factor;
    }

    public static int negativeSafeModulo(int number, final int modulus) {
        int temp = number;
        while(temp < 0) temp += modulus;
        return temp % modulus;
    }
}
