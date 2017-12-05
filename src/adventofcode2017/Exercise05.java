package adventofcode2017;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntUnaryOperator;

/**
 * See for the problem: Repositories/Opgave en dag05.txt
 * 
 * @author Piet
 */

public class Exercise05 {
    
    private int[] inputData;
    
    public static void main(String... args) {
        Exercise05 opgave05 = new Exercise05();
        IntUnaryOperator part1 = i -> 1;
        IntUnaryOperator part2 = i -> i >= 3 ? -1 : 1;
        int nrOfStepsToTakePart1 = opgave05.solve(part1);
        System.out.format("nr. of steps required: %,d%n", nrOfStepsToTakePart1);
        int nrOfStepsToTakePart2 = opgave05.solve(part2);
        System.out.format("nr. of steps required: %,d%n", nrOfStepsToTakePart2);
        
    }
    
    private Exercise05() {
        readInputdata();
    }
    
    private void readInputdata() {
        try {
            URL url = Exercise02.class.getResource("Repositories/inputExercise05.txt");
            Path path = Paths.get(url.toURI());
            inputData = Files.lines(path).mapToInt(Integer::parseInt).toArray();
        }
        catch (Exception e) {
            throw new RuntimeException("Can't read input data!!");
        }
    }
    
    private int solve(IntUnaryOperator f) {
        int[] copy = Arrays.copyOf(inputData, inputData.length);
        int steps = 0;
        int index = 0;
        while (index >= 0 && index < copy.length) {
            steps++;
            int delta = copy[index];
            copy[index] += f.applyAsInt(delta); // f.applyAsInt(delta);
            index += delta;
        }
        return steps;
    } 
}
