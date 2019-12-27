package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Moon {
    private int[] pos, vel;

    private Moon() {
        pos = new int[3];
        vel = new int[3];
    }

    private Moon(String str) {
        this();
        parseMoon(str);
    }

    private void parseMoon(String str) {
        String[] ar = str.split("[,=>]");
        for (int i = 0; i < pos.length; i++)
            pos[i] = Integer.parseInt(ar[2 * i + 1]);
    }

    private void applyGravity(Moon[] moons) {
        for (Moon m: moons)
            applyGravity(m);
    }

    private void applyGravity(Moon[] moons, int dim) {
        for (Moon m: moons)
            applyGravity(m, dim);
    }

    private void applyGravity(Moon m) {
        for (int i = 0; i < vel.length; i++)
            applyGravity(m, i);
    }

    private void applyGravity(Moon m, int dim) {
        vel[dim] += Integer.compare(m.pos[dim], pos[dim]);
    }

    private void applyVelocity() {
        for (int i = 0; i < pos.length; i++)
            applyVelocity(i);
    }

    private void applyVelocity(int dim) {
        pos[dim] += vel[dim];
    }

    private int getXEnergy(boolean potential) {
        int sum = 0;
        for (int i : potential? pos : vel)
            sum += Math.abs(i);
        return sum;
    }

    private int getPotentialEnergy() {
        return getXEnergy(true);
    }

    private int getKineticEnergy() {
        return  getXEnergy(false);
    }

    private int getTotalEnergy() {
        return getPotentialEnergy() * getKineticEnergy();
    }

    private static int getTotalEnergy(Moon[] moons) {
        int sum = 0;
        for (Moon m : moons)
            sum += m.getTotalEnergy();
        return sum;
    }

    static private int solvePart1() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day12.txt"));
        Moon[] moons = new Moon[4];
        for (int i = 0; i < 4; i++) {
            moons[i] = new Moon(reader.readLine());
        }
        reader.close();
        for (int i = 0; i < 1000; i++) {
            for (Moon m : moons)
                m.applyGravity(moons);
            for (Moon m : moons)
                m.applyVelocity();
        }
        return Moon.getTotalEnergy(moons);
    }

    private static long gcd(long a, long b) {
        int gcd = 1;
        for (int i = 2; i < Long.min(a, b); i++)
            if (a % i == 0 && b % i == 0)
                gcd = i;
        return gcd;
    }

    static private long solvePart2() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day12.txt"));
        Moon[] moons = new Moon[4], startState = new Moon[4];
        for (int i = 0; i < 4; i++) {
            String positions = reader.readLine();
            moons[i] = new Moon(positions);
            startState[i] = new Moon(positions);
        }

        int[] steps = new int[3];
        for (int dim = 0; dim < steps.length; dim++) {
            int time = 0;
            boolean isInInitialState;
            do {
                for (Moon m : moons)
                    m.applyGravity(moons, dim);
                for (Moon m : moons)
                    m.applyVelocity(dim);
                steps[dim] = ++time;
                isInInitialState = true;
                for (int i = 0; i < moons.length; i++) {
                    if (moons[i].pos[dim] != startState[i].pos[dim] || moons[i].vel[dim] != 0) {
                        isInInitialState = false;
                        break;
                    }
                }
            } while (!isInInitialState);
        }

        long result = 1;
        for (int step : steps) {
            result = result * (step / gcd(result, step));
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(solvePart1());
        System.out.println(solvePart2());
    }
}
