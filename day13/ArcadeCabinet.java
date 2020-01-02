package day13;

import intcode.InputProducer;
import intcode.IntCodeComputer;
import intcode.OutputListener;
import util.Point;
import java.util.HashMap;

public class ArcadeCabinet implements InputProducer, OutputListener {
    private HashMap<Point, Integer> tiles;
    private int outputCounter;
    private int x, y, score;
    private int xPaddle, xBall;

    private ArcadeCabinet() {
        tiles = new HashMap<>();
        outputCounter = xPaddle = xBall = x = y = score = 0;
    }

    @Override
    public long produceInput(IntCodeComputer computer) {
        return Integer.compare(xBall, xPaddle);
    }

    @Override
    public void outputProduced(long output) {
        int out = (int) output;
        if (outputCounter == 0)
            x = out;
        else if (outputCounter == 1)
            y = out;
        else if (x == -1 && y == 0)
            score = out;
        else {
            tiles.put(new Point(x, y), out);
            if (output == 3)
                xPaddle = x;
            else if (output == 4)
                xBall = x;
        }
        outputCounter = ++outputCounter % 3;
    }

    private int startGame() {
        IntCodeComputer game = new IntCodeComputer("inputs/day13.txt", this, this);
        game.runProgram();
        return (int) tiles.values().stream().filter(i -> i == 2).count();
    }

    private int runGame() {
        IntCodeComputer game = new IntCodeComputer("inputs/day13.txt", this, this);
        game.set(0, 2);
        game.runProgram();
        return score;
    }

    public static void main(String[] args) {
        ArcadeCabinet game = new ArcadeCabinet();
        System.out.println("Solution of day 13, part 1: " + game.startGame());
        System.out.println("Solution of day 13, part 2: " + game.runGame());
    }
}
