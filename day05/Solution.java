package day05;

import intcode.*;

public class Solution implements OutputListener{
    private int lastOutput;

    private Solution() {
        lastOutput = 0;
    }

    @Override
    public void outputProduced(int output) {
        if (lastOutput != 0)
            System.err.println("An error occured");
        lastOutput = output;
    }

    private int solve(int part) {
        new IntCodeComputer("inputs/day05.txt", () -> part == 1? 1 : 5, this).runProgram();
        return lastOutput;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 3; i++)
            System.out.println("Solution of day 5, part " + i + ": " + new Solution().solve(i));
    }
}
