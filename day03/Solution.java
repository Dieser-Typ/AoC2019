package day03;

import util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiFunction;

public class Solution {

    private static int handlePath(BufferedReader reader, int initParam, BiFunction<Point, Integer, Integer> f) throws IOException {
        String[] path = reader.readLine().split(",");
        int x = 0, y = 0, totalSteps = 0, param = initParam;
        for (String str: path) {
            char dir = str.charAt(0);
            int xDir = dir == 'R' ? 1 : dir == 'L' ? -1 : 0;
            int yDir = dir == 'U' ? 1 : dir == 'D' ? -1 : 0;
            int steps = Integer.parseInt(str.substring(1));
            while (steps-- > 0) {
                x += xDir;
                y += yDir;
                param = f.apply(new Point(x, y, ++totalSteps), param);
            }
        }
        return param;
    }

    private static int solve(int part) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day03.txt"));
        HashMap<Point, Integer> points = new HashMap<>();
        handlePath(reader,0,  (p, steps) -> {
            points.put(p, ++steps);
            return steps;
        });
        return handlePath(reader, Integer.MAX_VALUE, (p, minDist) -> {
            if (points.containsKey(p)) {
                int dist = part == 1? p.getManhattanDistance(new Point(0, 0)) : p.getSteps() + points.get(p);
                return Integer.min(dist, minDist);
            }
            return minDist;
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Solution of day 3, part 1: " + solve(1));
        System.out.println("Solution of day 3, part 2: " + solve(2));
    }

}
