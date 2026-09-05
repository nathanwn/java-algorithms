import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        List<Integer> masses = new ArrayList<>();
        while (in.hasNext()) {
            masses.add(in.nextInt());
        }
        out.println("part 1: " + part1(masses));
        out.println("part 2: " + part2(masses));
        out.close();
    }

    static long part1(List<Integer> masses) {
        long ans = 0;
        for (int mass : masses) {
            ans += getFuelRequired(mass);
        }
        return ans;
    }

    static long part2(List<Integer> masses) {
        long ans = 0;
        for (int mass : masses) {
            ans += getCummulativeFuelRequired(mass);
        }
        return ans;
    }

    static int getFuelRequired(int mass) {
        return Math.max(0, mass / 3 - 2);
    }

    static int getCummulativeFuelRequired(int mass) {
        if (mass == 0) return 0;
        int fuel = getFuelRequired(mass);
        return fuel + getCummulativeFuelRequired(fuel);
    }
}
