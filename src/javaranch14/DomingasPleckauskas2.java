/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Piet
 */
public class DomingasPleckauskas2 {
    
    BufferedImage buf;
    JPanel arrowAim, panel;
    Point fixedPoint;
    Point aimPoint;
    Point oldPoint;
    
    public static void main(String... args) {
        SwingUtilities.invokeLater(DomingasPleckauskas2::new);
    } 
    
    DomingasPleckauskas2() {
        fixedPoint = new Point(100, 300);
        aimPoint = new Point();
        oldPoint = new Point();
        
        try {
            buf = ImageIO.read(new File("D:\\Syls bestanden\\bezier.png"));
        } catch (IOException ex) {
            System.out.println("Can't load image!!!");
            return;
        }
        JFrame frame = new JFrame("DomingasPleckauskas2");
        
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                int w = this.getWidth(), h = this.getHeight();
                g.drawImage(buf, 0, 0, w, h, null);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 500);
            }
        };
        
        frame.setContentPane(panel);
        
        arrowAim = new JPanel() {
            Color color = new Color(0, 0, 0, 0);
            
            @Override
            public Color getBackground() {
                return color;
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                drawArrow(g2d);
                g2d.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return panel.getSize();
            }
        };
        
        panel.setOpaque(false);
        
        MouseMotionListener mml = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent m) {
                oldPoint = aimPoint;
                aimPoint = m.getPoint();
                panel.repaint();
            }
        };
        
        arrowAim.addMouseMotionListener(mml);
        
        panel.add(arrowAim);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void drawArrow(Graphics2D g2d) {
        int a = 7;
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.setXORMode(Color.BLUE);
        g2d.drawLine(fixedPoint.x, fixedPoint.y, oldPoint.x, oldPoint.y);
        g2d.drawLine(fixedPoint.x, fixedPoint.y, aimPoint.x, aimPoint.y);
//        System.out.println("In drawArrow");
    }
}

