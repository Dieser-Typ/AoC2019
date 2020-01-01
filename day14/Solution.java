package day14;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    static void reduceFirstChemicalCost(List<Reaction> reactions, List<Chemical> lst) {
        Chemical c = lst.remove(0);
        if (c.getName().equals("ORE")) {
            lst.add(c);
            return;
        }
        for (Reaction r : reactions) {
            if (r.output.getName().equals(c.getName())) {
                int count = (int) Math.ceil((double) c.getQuantity() / r.output.getQuantity());
                lst.addAll(r.input.stream().map(ch -> new Chemical(count * ch.getQuantity(), ch.getName())).collect(Collectors.toList()));
                c.changeQuantity(- count * r.output.getQuantity());
                break;
            }
        }
        lst.add(c);
    }

    static void reduceList(List<Chemical> lst) {
        for (int i = 0; i < lst.size(); i++) {
            Chemical c = lst.get(i);
            for (int j = i + 1; j < lst.size(); j++) {
                Chemical c2 = lst.get(j);
                if (c.getName().equals(c2.getName()))
                    c.changeQuantity(lst.remove(j--).getQuantity());
            }
        }
        lst.removeIf(c -> c.getQuantity() == 0);
    }

    static long getOreCost(List<Reaction> reactions, List<Chemical> lst) {
        while(containsOnlyOreAndNegativeCost(lst)) {
            reduceFirstChemicalCost(reactions, lst);
            reduceList(lst);
        }
        return lst.stream().filter(c -> c.getName().equals("ORE")).findFirst().orElse(new Chemical(0, "ORE")).getQuantity();
    }

    static boolean containsOnlyOreAndNegativeCost(List<Chemical> lst) {
        return lst.stream().anyMatch(c -> c.getQuantity() > 0 && !c.getName().equals("ORE"));
    }

    static long solvePart1() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day14.txt"));
        List<Reaction> reactions = reader.lines().map(Reaction::parseReaction).collect(Collectors.toList());
        return getOreCost(reactions, genFuelList(1));
    }

    static List<Chemical> genFuelList(int amount) {
        List<Chemical> lst = new ArrayList<>();
        lst.add(new Chemical(amount, "FUEL"));
        return lst;
    }

    static long solvePart2() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day14.txt"));
        List<Reaction> reactions = reader.lines().map(Reaction::parseReaction).collect(Collectors.toList());
        int maxFuel = 0;
        for (int i = 1; getOreCost(reactions, genFuelList(i)) <= 1000000000000L; i *= 2)
            maxFuel = i;
        for (int i = maxFuel / 2; i >= 1; i /= 2)
            if (getOreCost(reactions, genFuelList(maxFuel + i)) <= 1000000000000L)
                maxFuel += i;
        return maxFuel;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Solution of day 14, part 1: " + solvePart1());
        System.out.println("Solution of day 14, part 2: " + solvePart2());
    }
}
