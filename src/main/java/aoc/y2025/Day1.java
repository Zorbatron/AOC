package aoc.y2025;

import aoc.AOCDay;
import java.io.BufferedReader;
import java.util.PrimitiveIterator;

// You have a dial that starts at 5 and goes from 0 to 99.
// Each line of the input is L(num) or R(num).
// Count how many times the dial hits zero after each rotation.
public final class Day1 implements AOCDay {

    @Override
    public void run(BufferedReader input) {
        PrimitiveIterator.OfInt iterator = input.lines()
                .sequential()
                .filter(str -> !str.isBlank())
                .mapToInt(str -> {
                    String direction = str.substring(0, 1);
                    String number = str.substring(1);
                    return switch (direction) {
                        case "L" -> -Integer.parseInt(number);
                        case "R" -> Integer.parseInt(number);
                        default -> throw new RuntimeException("Unknown direction.");
                    };
                })
                .iterator();

        int dial = 50;
        int hitZero = 0;
        while (iterator.hasNext()) {
            // Skip full rotations
            int rotate = iterator.nextInt() % 100;
            if (dial + rotate < 0) {
                dial = (dial + rotate) + 100;
            } else if (dial + rotate > 99) {
                dial = (dial + rotate) - 100;
            } else {
                dial += rotate;
            }

            if (dial == 0) {
                hitZero += 1;
            }
        }

        System.out.println(hitZero);
    }
}
