/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

/**
 *
 * @author Piet
 */
public class KennethJavier {
    public static void main(String... args) {
        String b = "xxx    xxx    xxx    xxx   ";
        String w = "   xxx    xxx    xxx    xxx";
        String black = b + '\n';
        String white = w + '\n';
        String temp = black + white + black + white;
        String board = temp + temp;
        System.out.println(board);
        
    }
}
