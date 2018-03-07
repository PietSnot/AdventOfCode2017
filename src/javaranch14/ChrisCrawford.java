/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Piet
 */

public class ChrisCrawford {
    BufferedImage buf;
    GradientPaint paint = new GradientPaint(
                        new Point2D.Double(0, 0), 
                        Color.YELLOW, 
                        new Point2D.Double(300, 300), 
                        Color.BLUE
                  )
    ;
    Timer timer;
    int alpha = 255;
    int deltaAlpha = -3;
    Color currentColor = new Color(0, 0, 0, alpha);
    JPanel panel;
    
    public static void main(String... args) {
        SwingUtilities.invokeLater(ChrisCrawford::new);
    }
    
    ChrisCrawford() {
        panel = new JPanel() {
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setPaint(paint);
                g2d.fillRect(0, 0, panel.getWidth(), panel.getHeight());
                g2d.drawImage(buf, 50, 50, currentColor, null);
                g2d.dispose();
            }
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
                
        buf = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) buf.getGraphics();
        g2d.setColor(Color.RED);
        g2d.fillOval(0, 0, 200, 200);
        g2d.dispose();
        
        JFrame frame = new JFrame("Transparancy");
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        timer = new Timer(40, this::fade);
        timer.start();
    }
    
    private void fade(ActionEvent ae) {
        alpha += deltaAlpha;
        if (alpha < 0) {
            alpha = 0;
            deltaAlpha = -deltaAlpha;
        }
        else if (alpha > 255) {
            alpha = 255;
            deltaAlpha = -deltaAlpha;
        }
        currentColor = new Color(0, 0, 0, alpha);
        panel.repaint();
    }
}