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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Piet
 */
public class Exercise20 {
    
    List<Particle> particles = new ArrayList<>();
    Particle solutionPart1;
    int solutionPart2;
    
    public static void main(String... args) {
        Exercise20 opgave20 = new Exercise20();
        opgave20.solvePart1();
        String s = "particle staying most close to the origing: particle ";
        System.out.println(s + opgave20.solutionPart1.id);
        int attempts = 100_000;
        opgave20.solvePart2(attempts);
        System.out.format("after %,d attempts partivcles left: %,d%n", attempts, opgave20.solutionPart2);
    }
    
    private Exercise20() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise20.txt");
            Path path = Paths.get(url.toURI());
            List<String> list = Files.readAllLines(path);
            Pattern p = Pattern.compile("\\<[\\-0-9, ]+?\\>");
            processInputlines(list, p);
        }
        catch(IOException | URISyntaxException e) {
            throw new RuntimeException("Can't read input file!!!!");
        }
    }
    
    private void processInputlines(List<String> list, Pattern p) {
        int index = 0;
        Triple[] triple = new Triple[3];
        for (String line: list) {
            Matcher m = p.matcher(line);
            int i = 0;
            while (m.find()) {
                triple[i] = makeTriple(m.group());
                i++;
            }
            particles.add(new Particle(index, triple[0], triple[1], triple[2]));
            index++;
        }
    }
    
    private Triple makeTriple(String s) {
        String t = s.substring(1, s.length() - 1);
        String[] str = t.split(",");
        return new Triple(Integer.parseInt(str[0]), 
                          Integer.parseInt(str[1]), 
                          Integer.parseInt(str[2])
                         )
        ;
    }
    
    private void solvePart1() {
        int minimumManhattan = particles.stream()
                .mapToInt(p -> p.a.manhattan())
                .min()
                .getAsInt()
        ;
        List<Particle> particlesWithMinimumAccelleration = particles.stream()
                .filter(p -> p.a.manhattan() == minimumManhattan)
                .collect(Collectors.toList())
        ;
        // there is only one particle with minimum accelleration
        solutionPart1 = particlesWithMinimumAccelleration.get(0);
    }
    
    private void solvePart2(int attempts) {
        Set<Triple> positionsAlreadySeen = new HashSet<>();
        Set<Triple> positions = new HashSet<>();
        for (int i = 0; i < attempts; i++) {
            if (i % (attempts / 100) == 0) System.out.println("busy with i = " + i);
            positions.clear();
            positionsAlreadySeen.clear();
            for (Particle particle: particles) {
                if (!positions.add(particle.p)) positionsAlreadySeen.add(particle.p);
            }
            particles.removeIf(particle -> positionsAlreadySeen.contains(particle.p));
            updateParticles();
        }
        solutionPart2 = particles.size();
    }
    
    private void updateParticles() {
        particles.forEach(Particle::update);
    }
    
    //***********************************************************************
    // class Particle
    //***********************************************************************
    private class Particle {
        final int id;
        Triple p;
        Triple v;
        final Triple a;
        
        Particle(int id, Triple p, Triple v, Triple a) {
            this.id = id;
            this.p = p;
            this.v = v;
            this.a = a;
        }
        
        public void update() {
            v = v.add(a);
            p = p.add(v);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this == o) return true;
            if (!(o instanceof Particle)) return false;
            Particle part = (Particle) o;
            return id == part.id;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.id;
            return hash;
        }
    }
    
    //***********************************************************************
    // class Triple
    //***********************************************************************
    private class Triple implements Comparable<Triple> {
        private final int x;
        private final int y;
        private final int z;
        
        Triple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public Triple add(Triple other) {
            return new Triple(x + other.x, y + other.y, z + other.z);
        }
        
        public int manhattan() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (!(o instanceof Triple)) return false;
            Triple b = (Triple) o;
            return x == b.x && y == b.y && z == b.z;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + this.x;
            hash = 71 * hash + this.y;
            hash = 71 * hash + this.z;
            return hash;
        }
        
        @Override
        public int compareTo(Triple other) {
            return manhattan() < other.manhattan() ? -1 :
                   manhattan() == other.manhattan() ? 0 :
                   1
            ;         
        }
    }
}
