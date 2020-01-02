package day06;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class OrbitMap {
    HashMap<String, String> orbits;

    private OrbitMap() throws FileNotFoundException {
        orbits = new HashMap<>();
        new BufferedReader(new FileReader("inputs/day06.txt"))
                .lines()
                .forEach(s -> {
                    String[] orbit = s.split("\\)");
                    orbits.put(orbit[1], orbit[0]);});
    }

    private int countOrbits(String name) {
        if (orbits.containsKey(name))
            return countOrbits(orbits.get(name)) + 1;
        else return 0;
    }

    private int solvePart1() {
        return this.orbits
                .keySet()
                .stream()
                .mapToInt(this::countOrbits)
                .reduce(0, Integer::sum);
    }

    private int solvePart2() {
        int orbitCountYOU = countOrbits("YOU");
        int orbitCountSAN = countOrbits("SAN");
        int innerOrbitCount = Integer.min(orbitCountSAN, orbitCountYOU);
        int outerOrbitCount = Integer.max(orbitCountSAN, orbitCountYOU);
        String outerOrbit = orbitCountYOU > orbitCountSAN? "YOU" : "SAN";
        String innerOrbit = orbitCountYOU > orbitCountSAN? "SAN" : "YOU";

        while (outerOrbitCount-- > innerOrbitCount)
            outerOrbit = orbits.get(outerOrbit);
        while (!innerOrbit.equals(outerOrbit)) {
            innerOrbit = orbits.get(innerOrbit);
            outerOrbit = orbits.get(outerOrbit);
        }
        return orbitCountSAN + orbitCountYOU - 2 * countOrbits(innerOrbit) - 2;
    }

    public static void main(String[] args) throws FileNotFoundException {
        OrbitMap map = new OrbitMap();
        System.out.println("Solution of day 6, part 1: " + map.solvePart1());
        System.out.println("Solution of day 6, part 2: " + map.solvePart2());
    }
}
