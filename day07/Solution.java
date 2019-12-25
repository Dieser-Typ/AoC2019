package day07;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import intcode.*;

public class Solution implements InputProducer, OutputListener{

    private int lastOutput;
    private int[] currentSequence;
    private int currentSeqNr;
    private boolean settingSet;
    private int currentAmplifier;
    private int largestOutput;

    private Solution() {
        lastOutput = 0;
        currentSequence = null;
        currentSeqNr = 0;
        settingSet = false;
        currentAmplifier = 0;
        largestOutput = 0;
    }

    @Override
    public int produceInput() {
        return (settingSet = !settingSet)? currentSequence[currentAmplifier++] : lastOutput;
    }

    @Override
    public void outputProduced(int output) {
        lastOutput = output;
    }

    private void generateNewPhaseSettingSequence() {
        currentAmplifier = 0;
        largestOutput = Integer.max(largestOutput, lastOutput);
        lastOutput = 0;
        currentSequence = new int[5];
        List<Integer> settings = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        int x = 120;
        for (int i = 0; i < 5; i++)
            currentSequence[i] = settings.remove(currentSeqNr++ % x / (x = x / (5 - i)));
    }

    private int solvePart1() {
        for (int i = 0; i < 120; i++) {
            this.generateNewPhaseSettingSequence();
            for (int j = 0; j < 5; j++) {
                IntCodeComputer amplifier = new IntCodeComputer("inputs/day07.txt", this, this);
                amplifier.runProgram();
            }
        }
        return largestOutput;
    }

    public static void main(String[] args) {
        System.out.println("Solution of day 7, part 1: " + new Solution().solvePart1());
    }
}
