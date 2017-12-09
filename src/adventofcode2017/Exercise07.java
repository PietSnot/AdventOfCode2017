/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adventofcode2017;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import static java.util.stream.Collectors.*;

/**
 *
 * @author Piet
 */
public class Exercise07 {
    
    private Map<String, Node> names = new HashMap<>();
    Node firstNode;
    
    public static void main(String... args) {
        Exercise07 opgave07 = new Exercise07();
        opgave07.solvePart1();
        opgave07.solvePart2();
    }
    
    Exercise07() {
        readAndProcessInput();
    }
    
    private void readAndProcessInput() {
        try {
            URL url = getClass().getResource("Repositories/inputExercise07.txt");
            Path path = Paths.get(url.toURI());
            List<String> lines = Files.readAllLines(path);
//            String[] strings = {
//                "pbga (66)",
//                "xhth (57)",
//                "ebii (61)",
//                "havc (66)",
//                "ktlj (57)",
//                "fwft (72) -> ktlj, cntj, xhth",
//                "qoyq (66)",
//                "padx (45) -> pbga, havc, qoyq",
//                "tknk (41) -> ugml, padx, fwft",
//                "jptl (61)",
//                "ugml (68) -> gyxo, ebii, jptl",
//                "gyxo (61)",
//                "cntj (57)",
//            };
//            List<String> lines = Arrays.asList(strings);
            lines.forEach(this::processInputDetermineAllNodes);
            lines.forEach(this::processInputAddSubnotes);
            processInputDetermineFirstNode();
            processInputAddLevels();
            processInputAddSubtowerWeights();
        }
        catch (Exception ex) {
            throw new RuntimeException("Can't read input data!!");
        }
    }
    
    private void processInputDetermineAllNodes(String line) {
        Scanner scanner = new Scanner(line);
        String nodename = scanner.next();
        String w = scanner.next();
        int weight = Integer.parseInt(w.substring(1, w.length() - 1));
        Node node = new Node(nodename, weight);
        names.put(nodename, node);
    }
    
    private void processInputAddSubnotes(String line) {
        Node node = names.get(line.split("\\s+")[0]);
        if (!line.contains("->")) return;
        line = line.substring(line.indexOf(">") + 1);
        String[] strings = line.split(",");
        for (String s: strings) {
            node.addNode(names.get(s.trim()));
        }
    }
    
    private void processInputDetermineFirstNode() {
        List<Node> allNodes = new ArrayList<>(names.values());
        Set<Node> noBottomNodes = new HashSet<>();
        allNodes.stream().forEach(node -> noBottomNodes.addAll(node.subNodes));
        allNodes.removeAll(noBottomNodes);
        firstNode = allNodes.get(0);
        firstNode.level = 0;
    }
    
    private void processInputAddLevels() {
        LinkedList<Node> queue = new LinkedList<>();
        queue.addFirst(firstNode);
        firstNode.level = 0;
        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            for (Node n: node.subNodes) {
                n.level = node.level + 1;
                queue.addLast(n);
            }
        }
    }
    
    private Node getRoot() {
        Comparator<Node> comp = Comparator.comparing(node -> node.level);
        return names.values().stream()
                .collect(minBy(comp)).orElse(null)
        ;
    }
    
    private void processInputAddSubtowerWeights() {
        for (int level = determineMaxlevel(); level >= 0; level--) {
            for (Node node: names.values()) {
                if (node.level != level) continue;
                if (node.subNodes.isEmpty()) node.weightOfSubtowers = 0;
                else {
                    int totalWeight = 0;
                    for (Node n: node.subNodes) totalWeight += n.weight + n.weightOfSubtowers;
                    node.weightOfSubtowers = totalWeight;
                }
            }
        }
        if (names.values().stream().filter(n -> n.weightOfSubtowers == -1).count() != 0) {
            throw new RuntimeException("Not all nodes have a filled wos!!!!");
        }
    }
    
    private void solvePart1() {
        System.out.println("root node is: " + firstNode);
    }
    
    private void solvePart2() {
        NodePair wrong = getUnbalancedNode(new NodePair(0, firstNode));
        System.out.format("wrong node is: %s, should have a weight of: %,d%n", wrong.node, wrong.node.weight);
    }
    
    private int determineMaxlevel() {
        return names.values().stream()
                .mapToInt(node -> node.level)
                .max()
                .getAsInt()
        ;
    }
    
    private NodePair getUnbalancedNode(NodePair delta) {
//        if (node.subNodes.isEmpty()) return null;
        NodePair wrong = getWrongWeightedNode(delta.node);
        if (wrong == null) {
            // all subtrees are equal
            delta.node.weight -= delta.delta;
            return delta;
        }
        return getUnbalancedNode(new NodePair(wrong.delta, wrong.node));
    }
    
    private NodePair getWrongWeightedNode(Node node) {
        Comparator<Node> comparator = Comparator.comparing(Node::totalWeight);
        List<Node> list = node.subNodes.stream().sorted(comparator).collect(toList());
        // all equal?
        if (list.get(0).totalWeight() == list.get(list.size() - 1).totalWeight()) {
            return null;
        }
        // is the last one the wrong one?
        if (list.get(0).totalWeight() == list.get(1).totalWeight()) {
            return new NodePair(list.get(list.size() - 1).totalWeight() - list.get(0).totalWeight(), list.get(list.size() - 1));
        }
        // no, so the first one is wrong
        return new NodePair(list.get(0).totalWeight() - list.get(1).totalWeight(), list.get(0));
    }
    //*************************************************************
    static class Node {
        String name;
        int weight = -1;
        int level = -1;
        Set<Node> subNodes = new HashSet<>();
        int weightOfSubtowers = -1;
        
        Node(String name) {
            this.name = name;
        }
        
        Node(String name, int weight) {
            this.name = name;
            this.weight = weight;
        }
        
        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null) return false;
            if (!(other instanceof Node)) return false;
            Node node = (Node) other;
            return this.name.equals(node.name);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.name);
            return hash;
        }
        
        public boolean carries(Node node) {
            return subNodes.contains(node);
        }
        
        public void addNode(Node node) {
            subNodes.add(node);
        }
        
        public int totalWeight() {
            return weight + weightOfSubtowers;
        }
        
        @Override
        public String toString() {
            return String.format("%s (%d)", name, weight);
        }
    }  // einde class Node
    //******************************************************************
    
    static class NodePair {
        final int delta;
        final Node node;
        
        NodePair(int x, Node n) {
            delta = x;
            node = n;
        }
    }
}
