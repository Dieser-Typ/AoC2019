package day07;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import intcode.*;

public class Solution implements InputProducer, OutputListener{

    private int lastOutput;
    private int currentAmplifier;
    private Amplifier[] amplifiers;

    private Solution() {
        lastOutput = 0;
        currentAmplifier = 0;
        amplifiers = new Amplifier[5];
    }

    @Override
    public int produceInput(IntCodeComputer comp) {
        if (!(comp instanceof Amplifier))
            return 0;
        Amplifier amp = (Amplifier) comp;
        if (!amp.isSettingSet())
            return amp.setSetting();
        if (amplifiers[currentAmplifier].equals(comp)) {
            currentAmplifier = ++currentAmplifier % 5;
            return lastOutput;
        }
        amp.waitForInput();
        return 0;
    }

    @Override
    public void outputProduced(int output) {
        lastOutput = output;
    }

    private void setupAmlifiers(int seqNr, boolean inFeedbackLoopMode) {
        lastOutput = 0;
        List<Integer> settings = IntStream.range(0, 5).boxed().collect(Collectors.toList());
        int x = 120;
        for (int i = 0; i < 5; i++) {
            int phaseSetting = settings.remove(seqNr % x / (x = x / (5 - i)));
            if (inFeedbackLoopMode)
                phaseSetting += 5;
            amplifiers[i] = new Amplifier(phaseSetting, this);
        }
    }

    private int solve(int part) {
        int largestOutput = 0;
        for (int i = 0; i < 120; i++) {
            this.setupAmlifiers(i, part == 2);
            do {
                for (IntCodeComputer amplifier : amplifiers)
                    amplifier.runProgram();
            } while (amplifiers[4].isWaiting());
            largestOutput = Integer.max(largestOutput, lastOutput);
        }
        return largestOutput;
    }

    public static void main(String[] args) {
        System.out.println("Solution of day 7, part 1: " + new Solution().solve(1));
        System.out.println("Solution of day 7, part 2: " + new Solution().solve(2));
    }
}
