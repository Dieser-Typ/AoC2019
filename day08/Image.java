package day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Image {

    private int width;
    private int height;
    private List<int[][]> layers;
    private int[][] image;

    private Image(String imgPath, int width, int height) throws IOException {
        this.width = width;
        this.height = height;
        parseImage(imgPath);
        decode();
    }

    private void parseImage(String imgPath) throws IOException {
        layers = new ArrayList<>();
        String image = new BufferedReader(new FileReader(imgPath)).readLine();
        int size = width * height;
        for (int i = 0; i * size < image.length(); i++) {
            String layerStr = image.substring(i * size, (i+1) * size);
            int[][] layer = new int[height][width];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    layer[y][x] = Character.getNumericValue(layerStr.charAt(y * width + x));
                }
            }
            layers.add(layer);
        }
    }

    private int solvePart1() {
        int[][] fewestZerosLayer = null;
        int min = Integer.MAX_VALUE;
        for (int[][] layer : layers) {
            int zeros = countNumbers(layer, 0);
            if (zeros < min) {
                min = zeros;
                fewestZerosLayer = layer;
            }
        }
        int ones = countNumbers(fewestZerosLayer, 1);
        int twos = countNumbers(fewestZerosLayer, 2);
        return ones * twos;
    }

    private void decode() {
        image = layers.get(0);
        for (int[][] layer: layers)
            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++)
                    if (image[y][x] == 2)
                        image[y][x] = layer[y][x];
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int[] line: image) {
            for (int pixel : line) {
                str.append(pixel == 0 ? " " : "#");
            }
            str.append("\n");
        }
        return str.toString();
    }

    static private int countNumbers(int[][] layer, int number) {
        return Arrays.stream(layer)
                .mapToInt(ar ->
                        (int) Arrays.stream(ar)
                                .filter(i -> i == number)
                                .count())
                .sum();
    }

    public static void main(String[] args) throws IOException {
        Image img = new Image("inputs/day08.txt", 25, 6);
        System.out.println("Solution of day 8, part 1: " + img.solvePart1());
        System.out.println("Solution of day 8, part 2:");
        System.out.println(img);
    }
}
