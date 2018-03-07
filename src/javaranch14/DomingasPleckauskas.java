/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaranch14;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Piet
 */
public class DomingasPleckauskas {
    
    Path2D path;
    Point fixedPoint = new Point(50, 60);
    double shapeLength = 50d;
    double alpha = 0;
    BasicStroke bas;
    
    public static void main(String... args) {
        SwingUtilities.invokeLater(DomingasPleckauskas::new);
    }
    
    private DomingasPleckauskas() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                int w = this.getWidth(), h = this.getHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                Paint p =  new GradientPaint(new Point(0,0), Color.WHITE, new Point(w, h), new Color(255, 50, 50));
                g2d.setPaint(p);
                g2d.fillRect(0, 0, w, h);
                drawArrow(g2d);
                g2d.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 400);
            }
        };
        
        MouseMotionListener m = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent m) {
                determineAlpha(m);
                panel.repaint();
            }
        };
        
        panel.addMouseMotionListener(m);
        
        bas = new BasicStroke(1 / (float) shapeLength);
        
        createArrow();
        
        JFrame frame = new JFrame("Arrow following Mouse");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void createArrow() {
        path = new Path2D.Double();
        path.moveTo(0, 0);
        path.lineTo(0, 1);
        path.lineTo(0.1, 0.9);
        path.moveTo(0, 1);
        path.lineTo(-0.1, 0.9);
        path.closePath();
    }
    
    private void determineAlpha(MouseEvent m) {
        double deltaX = m.getX() - fixedPoint.x;
        double deltaY = m.getY() - fixedPoint.y;
        alpha = -Math.atan2(deltaX, deltaY);
    }
    
    private void drawArrow(Graphics2D g2d) {
        g2d.rotate(alpha, fixedPoint.x, fixedPoint.y);
        g2d.translate(fixedPoint.x, fixedPoint.y);
        g2d.scale(shapeLength, shapeLength);
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(bas);
        g2d.draw(path);
    }
}
