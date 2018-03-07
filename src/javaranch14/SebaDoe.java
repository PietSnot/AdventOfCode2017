/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Piet
 */
public class SebaDoe {

}

class Test1 {

    private static AtomicInteger sheepCount1 = new AtomicInteger(0);
    private static int sheepCount2 = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4); // w2
            for (int i = 0; i < 1_000_000; i++) {
                service.execute(()
                        -> {
                    sheepCount2++;
                    sheepCount1.getAndIncrement();
                    
                }); // w3
            }
            
        } 
        finally {
            if (service != null) {
                System.out.println("shutting service");
                service.shutdown();
                System.out.println("is service terminated? " + service.isTerminated());
            service.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("is service terminated? " + service.isTerminated());
            }
        }
        System.out.println(sheepCount1 + " " + sheepCount2);
    }
}
