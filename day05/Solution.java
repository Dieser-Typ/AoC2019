package day05;

import intcode.*;

public class Solution implements OutputListener{
    private long lastOutput;

    private Solution() {
        lastOutput = 0;
    }

    @Override
    public void outputProduced(long output) {
        if (lastOutput != 0)
            System.err.println("An error occured, unexpected Output: " + lastOutput);
        lastOutput = output;
    }

    private long solve(int part) {
        new IntCodeComputer("inputs/day05.txt", comp -> part == 1? 1 : 5, this).runProgram();
        return lastOutput;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 3; i++)
            System.out.println("Solution of day 5, part " + i + ": " + new Solution().solve(i));
    }
}
