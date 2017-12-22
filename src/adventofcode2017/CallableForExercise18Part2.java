/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Piet
 */
public class CallableForExercise18Part2 implements Callable<String> {
    long id;
    List<String> instructions;
    Map<String, Long> registers;
    Map<String, Instruction> basicCommands;
    LinkedTransferQueue<Long> sendTo, receiveFrom;
    TimeUnit timeUnit = TimeUnit.SECONDS;
    long maxWait = 10;
    long PC = 0;
    long nrOfSends = 0;
    String result;
    
    CallableForExercise18Part2(long id, LinkedTransferQueue sendTo, LinkedTransferQueue receiveFrom, List<String> instructions) {
        this.id = id;
        this.sendTo = sendTo;
        this.receiveFrom = receiveFrom;
        this.instructions = new ArrayList<>(instructions);
        fillRegisters();
        fillBasicCommands();
    }
    
    private void fillRegisters() {
        registers = new HashMap<>();
        registers.put("a", 0L);
        registers.put("b", 0L);
        registers.put("f", 0L);
        registers.put("i", 0L);
        registers.put("p", id);
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
        try {
            Long x = receiveFrom.poll(maxWait, timeUnit);
            if (x == null) {
                result = "Callable " + id + " waiting too long for a receive value!!!!\n";
                result += "nr of sends: " + nrOfSends;
                throw new RuntimeException(result);
            }
            registers.put(s[1], x);
            PC++;
        }
        catch (InterruptedException e) {
            result = "Callable: " + id + " interrupted during wait for receive!!!!";
            throw new RuntimeException(result);
        }
    }
    
    private void snd(String... s) {
        nrOfSends++;
        sendTo.offer(interprete(s[1]));
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

    @Override
    public String call() {
        try {
            while (PC >= 0 && PC < instructions.size()) {
                String[] s = instructions.get((int) PC).split("\\s+");
                basicCommands.get(s[0]).apply(s);
                result = "terminated: got outside program, nr of sends: " + nrOfSends;
            }
        }
        catch (RuntimeException e) {
            return e.getMessage();
        }
        return result;
    }
}
