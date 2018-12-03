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
import java.util.stream.Collectors;

/**
 * Met dit programma krijg ik 10.426 als antwoord op deel 2
 *
 * @author Piet
 */
public class Exercise23 {
    List<String> instructions;
    private long PC;
    Map<String, Long> registers;
    Map<String, Instruction> basicCommands;
    long solutionPart1 = 0;
    long solutionPart2 = 0;
    
    //----------------------------------------------------------------------
    public static void main(String... args) {
//        boolean originalInput = true;
        boolean originalInput = false;
        Exercise23 opgave23 = new Exercise23(originalInput);
//        opgave23.solvePart1();
//        System.out.format("aantal keren 'mul' aangeroepen: %,d%n", opgave23.solutionPart1);
        opgave23.solvePart2();
        System.out.format("slotwaarde register h: %,d%n", opgave23.solutionPart2);
    }
    
    //----------------------------------------------------------------------
    private void solvePart1() {
        reset();
        while (PC >= 0 && PC < instructions.size()) {
            String[] s = instructions.get((int) PC).split("\\s+");
            basicCommands.get(s[0]).apply(s);
        }
    }
    
    //----------------------------------------------------------------------
    private void solvePart2() {
        reset();
        registers.put("a", 1L);
//        int keer = 1;
//        int maxkeer = 1_000;
        while (PC >= 0 && PC < instructions.size()  /* && keer <= maxkeer */ ) {
//            if (PC == 20) {
//                System.out.println("*************************************************************************");
//                System.out.println("****************change in d**********************************************");
//                System.out.println("*************************************************************************");
//            }
//            System.out.format("PC: %d%n", PC);
            String[] s = instructions.get((int) PC).split("\\s+");
//            if (s.length == 1) {
//                System.out.format("Ïnstruction: %s%n", s[0]);
//            }
//            else System.out.format("Ïnstruction: %s %s %s%n", s[0], s[1], s[2]);
            basicCommands.get(s[0]).apply(s);
//            System.out.format("PC na verwerken instructie: %d%n", PC);
            if (PC == 32L) System.out.println("b = " + registers.get("b"));   
//            printRegisters();
//            keer++;
        }
        solutionPart2 = registers.get("h");
    }
    
    //----------------------------------------------------------------------
    private void printRegisters() {
        List<String> list = registers.keySet().stream().sorted().collect(Collectors.toList());
        list.forEach(str -> System.out.format("reg %s: %,d%n", str, registers.get(str)));
        System.out.println("**************************************************");
    }
    
    //----------------------------------------------------------------------
    private Exercise23(boolean originalInput) {
        String s = originalInput ? "Repositories/inputExercise23.txt" :
                                   "Repositories/inputExercise23optimized.txt"
        ;
        fillBasicCommands();
        try {
            URL url = getClass().getResource(s);
            Path path = Paths.get(url.toURI());
            instructions = Files.readAllLines(path);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read the input file!!!!");
        }
        fillRegisters(); 
    }
    
    //----------------------------------------------------------------------
    private void fillBasicCommands() {
        basicCommands = new HashMap<>();
        basicCommands.put("nop", this::nop);
        basicCommands.put("set", this::set);
        basicCommands.put("sub", this::sub);
        basicCommands.put("mul", this::mul);
        basicCommands.put("jnz", this::jnz);
        basicCommands.put("mod", this::mod);
        basicCommands.put("div", this::div);
    }
    
    //----------------------------------------------------------------------
    private void fillRegisters() {
        registers = new HashMap<>();
        for (String instruction: instructions) {
            String[] s = instruction.trim().split("\\s+");
            if (!instruction.equals("nop")) {
                System.out.println(instruction);
                char c = s[1].charAt(0);
                if (Character.isLetter(c)) registers.put(s[1], 0L);
            };
        }
        System.out.println("*************************************");
    }
    
    //----------------------------------------------------------------------
    private void mod(String... s) {
        registers.put(s[1], registers.get(s[1]) % interprete(s[2]));
        PC++;
    }
    
    //----------------------------------------------------------------------
    private void div(String... s) {
        registers.put(s[1], registers.get(s[1]) / interprete(s[2]));
    }
    
    //----------------------------------------------------------------------
    private void nop(String... s) {
        PC++;
    }
    //----------------------------------------------------------------------
    private void mul(String... s) {
        registers.put(s[1], registers.get(s[1]) * interprete(s[2]));
        PC++;
        solutionPart1++;
    }
    
    //----------------------------------------------------------------------
    private void sub(String... s) {
        registers.put(s[1], registers.get(s[1]) - interprete(s[2]));
        PC++;
    }
    
    //----------------------------------------------------------------------
    private void set(String... s) {
        registers.put(s[1], interprete(s[2]));
        PC++;
    }
    
    //----------------------------------------------------------------------
    private void jnz(String... s) {
        if (interprete(s[1]) != 0) {
            long x = interprete(s[2]);
            PC += x;
        }
        else PC++;
    }
    
    //----------------------------------------------------------------------
    private long interprete(String s) {
        try {
            long x = Long.parseLong(s);
            return x;
        }
        catch(NumberFormatException e) {
            return registers.get(s);
        }
    }
    
    //----------------------------------------------------------------------
    private void reset() {
        PC = 0;
        fillRegisters();
    }
    
}  // end of class Exercise18

