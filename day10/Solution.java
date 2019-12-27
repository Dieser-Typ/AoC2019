package day10;

import util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solution {

    private List<Point> asteroids;
    private Point monitoringStation;

    private Solution() {
        parseMap();
        monitoringStation = findMonitoringStaion();
    }

    private void parseMap() {
        asteroids = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("inputs/day10.txt"));
            for (int y = 0; reader.ready(); y++) {
                char[] line = reader.readLine().toCharArray();
                for (int x = 0; x < line.length; x++)
                    if (line[x] == '#')
                        asteroids.add(new Point(x, y));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Point findMonitoringStaion() {
        return asteroids.stream().reduce((a1, a2) -> a1.getVisiblePoints(asteroids) > a2.getVisiblePoints(asteroids)? a1 : a2).orElse(new Point(-1, -1));
    }

    private int solvePart1() {
        return monitoringStation.getVisiblePoints(asteroids);
    }

    private int solvePart2() {
        asteroids.sort(Comparator
                .comparingDouble(monitoringStation::getAngle)
                .thenComparingInt(a -> a.getManhattanDistance(monitoringStation)));
        double lastAngle = -1;
        Point removed = null;
        int removedPoints = 0;
        while (removedPoints < 200) {
            for (int i = 0; i < asteroids.size() && removedPoints < 200; i++) {
                double currentAngle = monitoringStation.getAngle(asteroids.get(i));
                if (currentAngle != lastAngle) {
                    removed = asteroids.remove(i--);
                    removedPoints++;
                    lastAngle = currentAngle;
                }
            }
        }
        return 100 * removed.getX() + removed.getY();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println("Solution of day 10, part 1: " + s.solvePart1());
        System.out.println("Solution of day 10, part 2: " + s.solvePart2());

    }
}
