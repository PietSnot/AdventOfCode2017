/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author Piet
 */
public class AndreyBelkin {
    public static void main(String... args) throws Exception {
        Path path = Paths.get("d:/javaprogs/andrey.txt");
        Files.lines(path)
                .flatMap(p -> Stream.of(p.split(",")))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
