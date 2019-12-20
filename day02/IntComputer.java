package day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

class IntComputer {
    private int[] prog;

    IntComputer(String programPath) {
        prog = parseProg(programPath);
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
            return new int[] {99};
        }
    }

    void runProgram() {
        int instructionPointer = 0;
        while (prog[instructionPointer] != 99)
            instructionPointer += processOpcode(instructionPointer);
    }

    private int processOpcode(int instructionPointer) {
        int param1 = prog[prog[instructionPointer + 1]];
        int param2 = prog[prog[instructionPointer + 2]];
        int destAddress = prog[instructionPointer + 3];
        switch(prog[instructionPointer]) {
            case 1:
                add(param1, param2, destAddress);
                break;
            case 2:
                multiply(param1, param2, destAddress);
                break;
            default: System.err.println("Unknown opcode. Something went wrong!");
        }
        return 4;
    }

    private void add(int param1, int param2, int destAddress) {
        prog[destAddress] = param1 + param2;
    }

    private void multiply(int param1, int param2, int destAddress) {
        prog[destAddress] = param1 * param2;
    }

    void replaceValue(int addr, int value) {
        prog[addr] = value;
    }

    int getValue(int address) {
        return prog[address];
    }
}
