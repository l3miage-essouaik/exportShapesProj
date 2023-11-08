package edu.uga.miage.m1.polygons.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class DrawingPanel extends JPanel {
    private transient List<SimpleShape> listOfShapes;

    public DrawingPanel(List<SimpleShape> listOfShapes) {
        this.listOfShapes = listOfShapes;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (SimpleShape shape : listOfShapes) {
            shape.draw(g2);
        }
    }
}
