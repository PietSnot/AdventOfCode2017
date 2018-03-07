/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Piet
 */
public class TrentGreen {
   
    public static void main(String... args) {
        List<Subtitle> list = new ArrayList<>();
        Path path = Paths.get("D:\\JavaProgs\\TrentGreen.txt");
        try (BufferedReader buf = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
            String s = "";
            while (s != null) {
                s = buf.readLine();
                list.add(Subtitle.parse(s));
            }
        } 
        catch (Exception e) {
            System.out.println("Can't open input file");
        }
        System.out.println(list);
    }
}

class Subtitle {
    double start;
    String text;
    double duration;
    final static String regexStart = "^\\d+(\\.\\d{1,3})?";
//    final static String regexStart = "^\\d+\\.\\d+";
    final static Pattern p1 = Pattern.compile(regexStart);
    final static String regexText = "\\((.*?)\\)";
    final static Pattern p2 = Pattern.compile(regexText);
    final static String regexEnd = "\\[(.*?)\\]";
    final static Pattern p3 = Pattern.compile(regexEnd);
    
    Subtitle(double s, String str, double d) {
        start = s;
        text = str;
        duration = d;
    }
    
    public static Subtitle parse(String toParse) {
        System.out.println("**************************8");
        System.out.println("string to parse: " + toParse);
        Matcher m1 = p1.matcher(toParse);
        Matcher m2 = p2.matcher(toParse);
        Matcher m3 = p3.matcher(toParse);
        double start = -1;
        if (m1.find()) {
            start = Double.parseDouble(m1.group());
            System.out.println("start found at index: " + m1.start());
        }
        System.out.println("start = " + start);
        String text = "not found";
        if (m2.find()) {
            text = m2.group();
            System.out.println("text found at index: " + m2.start());
        }
        System.out.println("text = " + text);
        double end = -1;
        if (m3.find()) {
            String f = m3.group();
            end = Double.parseDouble(f.substring(1, f.length() - 1));
            System.out.println("end found at index: " + m3.start());
        }
        System.out.println("end = " + end);
        System.out.println("**************************8");
        return new Subtitle(start, text, end);
    }
    
    @Override
    public String toString() {
        return String.format("start: %f, text = %s, end = %f", start, text, duration);
    }
}
