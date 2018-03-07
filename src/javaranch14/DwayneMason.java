/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaranch14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Piet
 */
public class DwayneMason {

}

class Jframe extends JFrame {

    private JTextField text;
    private JButton submit;
    private JLabel label;
    Boolean draw = true;

    public Jframe() {
        view();

        //set title of jframe
        setTitle("My classwork JFrame");
        //set size of JFrame
        setSize(800, 600);
        //set close action
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void view() {
        //creates jpanel
        JPanel panel = new JPanel();
        //adds panel to jframe
        getContentPane().add(panel);
        //creates color of jpanel
        panel.setBackground(Color.CYAN);

        //creates jlabel
        label = new JLabel("Enter your favorite number:  ");
        //sets color of text
        label.setForeground(Color.PINK);
        //adds jlabel to panel
        panel.add(label);

        //creates text field
        text = new JTextField();
        //sets size of textfield
        text.setPreferredSize(new Dimension(200, 30));
        //adds textfield to panel
        panel.add(text);

        //creates a submit button
        submit = new JButton("Submit");
        submit.addActionListener(new submitListener());
        //names the button
        panel.add(submit);
        panel.add(new circle());
    }

    class submitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int num = Integer.parseInt(text.getText());

            repaint();
        }
    }

    public class circle extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(250, 250);
        }
        
        @Override
        public Color getBackground() {
            return new Color(0,0,0,0);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawOval(100, 100, 100, 100);
            g.setColor(Color.PINK);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Jframe().setVisible(true);
            }
        });

    }

}
