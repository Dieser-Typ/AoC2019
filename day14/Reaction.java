package day14;

import java.util.ArrayList;
import java.util.List;

public class Reaction {
    List<Chemical> input;
    Chemical output;

    Reaction(List<Chemical> input, Chemical output) {
        this.input = input;
        this.output = output;
    }

    static Reaction parseReaction(String str) {
        String[] split = str.split("=>");
        Chemical output = Chemical.parseChemical(split[1].trim());
        split = split[0].split(",");
        List<Chemical> input = new ArrayList<>();
        for (String s: split)
            input.add(Chemical.parseChemical(s.trim()));
        return new Reaction(input, output);
    }
}
