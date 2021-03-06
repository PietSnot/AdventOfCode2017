/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Piet
 */
public class Exercise18 {
    
    List<String> instructions;
    private long PC;
    Map<String, Long> registers;
    Map<String, Instruction> basicCommands;
    long lastSoundPlayed;
    boolean firstRecovery;
    long total = 0;
    
    public static void main(String... args) {
        Exercise18 opgave18 = new Exercise18();
        opgave18.solvePart1();
        System.out.println("aantal instructies verwerkt: " + opgave18.total);
        opgave18.solvePart2();
    }
    
    private void solvePart1() {
        firstRecovery = false;
        PC = 0;
        while (PC >= 0 && PC < instructions.size() && !firstRecovery) {
            String[] s = instructions.get((int) PC).split("\\s+");
            basicCommands.get(s[0]).apply(s);
            total++;
        }
    }
    
    private void solvePart2() {
        LinkedTransferQueue sendTo = new LinkedTransferQueue();
        LinkedTransferQueue receiveFrom = new LinkedTransferQueue();
        CallableForExercise18Part2 zero = new CallableForExercise18Part2(
              0L, sendTo, receiveFrom, instructions);
        CallableForExercise18Part2 one = new CallableForExercise18Part2(
              1L, receiveFrom, sendTo, instructions);
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> futureZero = service.submit(zero);
        Future<String> futureOne = service.submit(one);
        try {
            String solution = futureOne.get(10L, TimeUnit.MINUTES);
            System.out.println("part 2: " + solution);
            String x = futureZero.get(10L, TimeUnit.MINUTES);
            System.out.println("Callable 0 also finished, " + x);
            service.shutdown();
            service.awaitTermination(10L, TimeUnit.SECONDS);
            System.out.println("Everything finished!");
        }
        catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    
    private Exercise18() {
        fillBasicCommands();
        try {
            URL url = getClass().getResource("Repositories/inputExercise18.txt");
            Path path = Paths.get(url.toURI());
            instructions = Files.readAllLines(path);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read the input file!!!!");
        }
        fillRegisters(); 
    }
    
    private void fillBasicCommands() {
        basicCommands = new HashMap<>();
        basicCommands.put("add", this::add);
        basicCommands.put("set", this::set);
        basicCommands.put("mul", this::mul);
        basicCommands.put("mod", this::mod);
        basicCommands.put("rcv", this::rcv);
        basicCommands.put("jgz", this::jgz);
        basicCommands.put("snd", this::snd);
    }
    
    private void fillRegisters() {
        registers = new HashMap<>();
        for (String instruction: instructions) {
            String[] s = instruction.split("\\s+");
            char c = s[1].charAt(0);
            if (Character.isLetter(c)) registers.put(s[1], 0L);
        }
    }
    
    private void mul(String... s) {
        registers.put(s[1], registers.get(s[1]) * interprete(s[2]));
        PC++;
    }
    
    private void add(String... s) {
        registers.put(s[1], registers.get(s[1]) + interprete(s[2]));
        PC++;
    }
    
    private void set(String... s) {
        registers.put(s[1], interprete(s[2]));
        PC++;
    }
    
    private void mod(String... s) {
        registers.put(s[1], registers.get(s[1]) % interprete(s[2]));
        PC++;
    }
    
    private void jgz(String... s) {
        if (interprete(s[1]) > 0) {
            long x = interprete(s[2]);
            PC += x;
        }
        else PC++;
    }
    
    private void rcv(String... s) {
        long x = interprete(s[1]);
        if (x > 0) {
            System.out.println("first value recovered: " + lastSoundPlayed);
            firstRecovery = true;
        }
        PC++;
    }
    
    private void snd(String... s) {
        lastSoundPlayed = interprete(s[1]);
        PC++;
    }
    
    private long interprete(String s) {
        try {
            long x = Long.parseLong(s);
            return x;
        }
        catch(NumberFormatException e) {
            return registers.get(s);
        }
    }
    
    private void reset() {
        PC = 0;
    }
    
}  // end of class Exercise18


