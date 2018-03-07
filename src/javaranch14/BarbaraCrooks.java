/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Piet
 */
public class BarbaraCrooks {
    public static void main(String... args) {
        List<String> list = new ArrayList<>();
        try {
            Path path = Paths.get("D:\\JavaProgs\\JavaApplication14\\src\\javaapplication14\\flup.txt");
            list = Files.readAllLines(path);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
        String s = list.get(0);
        String age = "Age=\"";
        int start = s.indexOf(age) + age.length(); 
        int end = s.indexOf("\"", start); 
        String value = s.substring(start, end); 
        System.out.println(value);
    }    
}
