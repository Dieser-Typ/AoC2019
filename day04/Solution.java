package day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Solution {
    private static int solve(int part) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("inputs/day04.txt"));
        int[] range = Arrays.stream(reader.readLine().split("-")).mapToInt(Integer::parseInt).toArray();
        int count = 0;
        for (int i = Math.max(range[0], 100000); i <= Math.min(range[1], 999999); i++) {
            boolean hasDouble = false, decreases = false;
            char[] chars = Integer.toString(i).toCharArray();
            for (int j = 0; j < 5; j++) {
                if (chars[j + 1] == chars[j] && (part != 2 || (j == 0 || chars[j-1] != chars[j]) && (j == 4 || chars[j] != chars[j+2])))
                    hasDouble = true;
                if (chars[j + 1] < chars[j])
                    decreases = true;
            }
            if (!decreases && hasDouble)
                count++;
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Solution of day 4, part 1: " + solve(1));
        System.out.println("Solution of day 4, part 2: " + solve(2));
    }
}
