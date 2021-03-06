package intcode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IntCodeComputer {

    private List<Long> program;
    private int instructionPointer;
    private int relativeBase;
    private InputProducer inputProducer;
    private OutputListener outputListener;
    private boolean isWaiting;

    public IntCodeComputer(String programPath) {
        parseProgram(programPath);
        instructionPointer = 0;
        relativeBase = 0;
        inputProducer = null;
        outputListener = null;
        isWaiting = false;
    }

    public IntCodeComputer(String programPath, InputProducer inProd, OutputListener outList) {
        this(programPath);
        inputProducer = inProd;
        outputListener = outList;
    }

    private void parseProgram(String programPath) {
        try {
            program = Arrays.stream(
                    new BufferedReader(new FileReader(programPath))
                            .readLine()
                            .split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runProgram() {
        isWaiting = false;
        while (get(instructionPointer) != 99 && !isWaiting) {
            int instLength = processOpcode();
            instructionPointer += instLength;
        }
    }

    private int processOpcode() {
        int opcode = (int) get(instructionPointer) % 100;
        switch (opcode) {
            case 1:
                add(); return 4;
            case 2:
                multiply(); return 4;
            case 3:
                input(); return isWaiting? 0: 2;
            case 4:
                output(); return 2;
            case 5: case 6:
                return jump(opcode == 5)? 0: 3;
            case 7:
                lessThan(); return 4;
            case 8:
                equals(); return 4;
            case 9:
                adjustRelativeBase(); return 2;
            default:
                throw new UnsupportedOperationException("Unknown opcode: " + opcode);
        }
    }

    private long param (int paramOffset, boolean isWriteParam) {
        int mode = (int) (get(instructionPointer) / Math.pow(10, paramOffset + 1) % 10);
        long param = get(instructionPointer + paramOffset);
        if (mode == 1)
            return param;
        long address = mode == 0? param : param + relativeBase;
        return isWriteParam? address : get((int) address);
    }

    private long param(int paramOffset) {
        return param(paramOffset, false);
    }

    private void add() {
        set((int) param(3, true), param(1) + param(2));
    }

    private void multiply() {
        set((int) param(3, true), param(1) * param(2));
    }

    private void input() {
        long input;
        if (inputProducer != null) {
            input = inputProducer.produceInput(this);
            if (isWaiting)
                return;
        } else {
            System.out.println("Waiting for Input...");
            input = new Scanner(System.in).nextLong();
        }
        set((int) param(1, true), input);
    }

    private void output() {
        if (outputListener != null)
            outputListener.outputProduced(param(1));
        else
            System.out.println(param(1));
    }

    private boolean jump(boolean condition) {
        if (param(1) != 0 == condition) {
            instructionPointer = (int) param(2);
            return true;
        }
        return false;
    }

    private void lessThan() {
        set((int) param(3, true), param(1) < param(2)? 1: 0L);
    }

    private void equals() {
        set((int) param(3, true), param(1) == param(2)? 1: 0L);
    }

    private void adjustRelativeBase() {
        relativeBase += param(1);
    }

    public long get(int address) {
        return address >= program.size()? 0 : program.get(address);
    }

    public void set(int address, long value) {
        while (address >= program.size())
            program.add(0L);
        program.set(address, value);
    }

    public void waitForInput() {
        isWaiting = true;
    }

    public boolean isWaiting() {
        return isWaiting;
    }
}
