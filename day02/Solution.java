package day02;

import intcode.IntCodeComputer;

public class Solution {
    private static long replaceAndRun(int noun, int verb) {
        IntCodeComputer c = new IntCodeComputer("inputs/day02.txt");
        c.replaceValue(1, noun);
        c.replaceValue(2, verb);
        c.runProgram();
        return c.get(0);
    }

    private static int solvePart2() {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                long output = replaceAndRun(noun, verb);
                if (output == 19690720)
                    return 100 * noun + verb;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Solution of day 2, part 1: " + replaceAndRun(12, 2));
        System.out.println("Solution of day 2, part 2: " + solvePart2());
    }
}
