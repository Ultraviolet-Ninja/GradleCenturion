package bomb.tools.number;

public class MathUtils {
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
}
