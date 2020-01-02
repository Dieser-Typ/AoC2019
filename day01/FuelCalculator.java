package day01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FuelCalculator {
    private static int solve(int part) throws FileNotFoundException {
        return new BufferedReader(new FileReader("inputs/day01.txt"))
                .lines()
                .mapToInt(Integer::parseInt)
                .map(part == 1?
                        FuelCalculator::calcFuel :
                        FuelCalculator::calcAdditionalFuel)
                .reduce(0, Integer::sum);
    }

    private static int calcFuel(int mass) {
        return mass / 3 - 2;
    }

    private static int calcAdditionalFuel(int mass) {
        int fuel = 0;
        while ((mass = calcFuel(mass)) > 0)
            fuel += mass;
        return fuel;
    }

    private static int calcAdditionalFuelRec(int mass) {
        return (mass = calcFuel(mass)) > 0? mass + calcAdditionalFuelRec(mass) : 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Solution of day 1, part 1: " + solve(1));
        System.out.println("Solution of day 1, part 2: " + solve(2));
    }
}
