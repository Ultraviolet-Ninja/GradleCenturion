package bomb.tools.number;

public class MathUtils {
    public static final int HASHING_NUMBER = 5501;

    public static boolean isPerfectSquare(int num) {
        double root = Math.sqrt(num);
        return (root - Math.floor(root)) == 0;
    }

    public static boolean isPrime(int num) {
        if (num <= 1)
            return false;
        if (num <= 3)
            return true;
        if(num % 2 == 0 || num % 3 == 0)
            return false;

        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0)
                return false;
        }
        return true;
    }
}
