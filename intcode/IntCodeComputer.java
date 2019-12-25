package intcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class IntCodeComputer {
    private int[] prog;
    private int instructionPointer;
    private InputProducer inputProducer;
    private OutputListener outputListener;
    private boolean isWaiting;

    public IntCodeComputer(String programPath) {
        prog = parseProg(programPath);
        instructionPointer = 0;
        inputProducer = null;
        outputListener = null;
        isWaiting = false;
    }

    public IntCodeComputer(String programPath, InputProducer inProd, OutputListener outList) {
        this(programPath);
        inputProducer = inProd;
        outputListener = outList;
    }

    private int[] parseProg(String programPath) {
        try {
            return Arrays.stream(
                    new BufferedReader(new FileReader(programPath))
                            .readLine()
                            .split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (Exception e) {
            // if parsing fails, return a prog which immediately halts
            return new int[]{99};
        }
    }

    public void runProgram() {
        isWaiting = false;
        while (prog[instructionPointer] != 99 && !isWaiting) {
            int instLength = processOpcode();
            instructionPointer += instLength;
        }
    }

    private int processOpcode() {
        int opcode = prog[instructionPointer];
        switch (opcode % 100) {
            case 1:
                add();
                return 4;
            case 2:
                multiply();
                return 4;
            case 3:
                input(); return isWaiting? 0: 2;
            case 4:
                output(); return 2;
            case 5:
                return jump(true)? 0: 3;
            case 6:
                return jump(false)? 0: 3;
            case 7:
                lessThan(); return 4;
            case 8:
                equals(); return 4;
            default:
                System.err.println(opcode);
                throw new UnsupportedOperationException();
        }
    }

    private int param (int paramOffset, boolean isWriteParam) {
        int mode = prog[instructionPointer] / 10 / (int) Math.pow(10, paramOffset) % 10;
        int param = prog[instructionPointer + paramOffset];
        return mode == 1 || isWriteParam? param : prog[param];
    }

    private int param(int paramOffset) {
        return param(paramOffset, false);
    }

    private void add() {
        prog[param(3, true)] = param(1) + param(2);
    }

    private void multiply() {
        prog[param(3, true)] = param(1) * param(2);
    }

    private void input() {
        int input;
        if (inputProducer != null) {
            input = inputProducer.produceInput(this);
            if (isWaiting)
                return;
        } else {
            System.out.println("Waiting for Input...");
            input = new Scanner(System.in).nextInt();
        }
        prog[param(1, true)] = input;
    }

    private void output() {
        if (outputListener != null)
            outputListener.outputProduced(param(1));
        else
            System.out.println(param(1));
    }

    private boolean jump(boolean condition) {
        if (param(1) != 0 == condition) {
            instructionPointer = param(2);
            return true;
        }
        return false;
    }

    private void lessThan() {
        prog[param(3, true)] = param(1) < param(2)? 1: 0;
    }

    private void equals() {
        prog[param(3, true)] = param(1) == param(2)? 1: 0;
    }

    public void replaceValue(int addr, int value) {
        prog[addr] = value;
    }

    public int getValue(int address) {
        return prog[address];
    }

    public void waitForInput() {
        isWaiting = true;
    }

    public boolean isWaiting() {
        return isWaiting;
    }
}
