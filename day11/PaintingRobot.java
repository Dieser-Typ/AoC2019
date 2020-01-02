package day11;

import intcode.InputProducer;
import intcode.IntCodeComputer;
import intcode.OutputListener;
import util.Point;
import java.util.HashMap;

public class PaintingRobot implements OutputListener, InputProducer {
    private boolean outputisColor;
    private HashMap<Point, Integer> paintedPanels;
    private Point currentPoint;
    private int dirX, dirY;
    private int part;
    private boolean hasStarted;

    private PaintingRobot() {
        outputisColor = true;
        paintedPanels = new HashMap<>();
        currentPoint = new Point(0, 0);
        dirY = -1;
        dirX = 0;
        part = 0;
    }

    @Override
    public long produceInput(IntCodeComputer computer) {
        if (!hasStarted) {
            hasStarted = true;
            return part == 1? 0 : 1;
        }
        if (paintedPanels.containsKey(currentPoint)) {
            return paintedPanels.get(currentPoint);
        }
        return 0;
    }

    @Override
    public void outputProduced(long output) {
        if (outputisColor)
            paintedPanels.put(currentPoint, (int) output);
        else {
            if (output == 0)
                turnLeft();
            else
                turnRight();
            currentPoint = new Point(currentPoint.getX() + dirX, currentPoint.getY() + dirY);
        }
        outputisColor = !outputisColor;
    }

    private void turnLeft() {
        if (dirX == 0) {
            dirX = +dirY;
            dirY = 0;
        } else {
            dirY = -dirX;
            dirX = 0;
        }
    }

    private void turnRight() {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }

    private void run() {
        new IntCodeComputer("inputs/day11.txt", this, this).runProgram();
    }

    @Override
    public String toString() {
        int minX = 0, minY = 0, maxX = 0, maxY = 0;
        for (Point p : paintedPanels.keySet()) {
            minX = Integer.min(minX, p.getX());
            maxX = Integer.max(maxX, p.getX());
            minY = Integer.min(minY, p.getY());
            maxY = Integer.max(maxY, p.getY());
        }
        StringBuilder result = new StringBuilder();
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                Point p = new Point(j, i);
                if (paintedPanels.containsKey(p) && paintedPanels.get(p) == 1)
                    result.append("#");
                else
                    result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    private int solvePart1() {
        part = 1;
        run();
        return paintedPanels.size();
    }

    private String solvePart2() {
        part = 2;
        run();
        return this.toString();
    }

    public static void main(String[] args) {
        System.out.println("Solution of day 11, part 1: " + new PaintingRobot().solvePart1());
        System.out.println("Solution of day 11, part 2:");
        System.out.println(new PaintingRobot().solvePart2());
    }
}
