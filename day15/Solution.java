package day15;

import intcode.InputProducer;
import intcode.IntCodeComputer;
import intcode.OutputListener;

public class Solution implements InputProducer, OutputListener {
    Node currentNode;
    Node oxygenStation;
    Node root;
    int steps;

    Solution() {
        root = new Node();
        currentNode = root;
        oxygenStation = null;
    }

    @Override
    public long produceInput(IntCodeComputer computer) {
        return currentNode.nextMovement() + 1;
    }

    @Override
    public void outputProduced(long output) {
        currentNode = currentNode.move(output);
        if (output == 2) {
            oxygenStation = currentNode;
            steps = currentNode.getSteps();
        }
    }

    private int getSteps() {
        return steps;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        IntCodeComputer pc = new IntCodeComputer("inputs/day15.txt", s, s);
        pc.runProgram();
        System.out.println("Solution of day 15, part 1: " + s.getSteps());
    }
}
