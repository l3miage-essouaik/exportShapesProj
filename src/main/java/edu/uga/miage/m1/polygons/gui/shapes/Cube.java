package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube implements SimpleShape, Visitable {

    int mX;

    int mY;
    boolean shapeMoved;
    public List<Integer> previousXPositions = new ArrayList<>();
    public List<Integer> previousYPositions = new ArrayList<>();

    public Cube(int x, int y) {
        mX = x - 25;
        mY = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * 
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        CubePanel cube = new CubePanel(50, mX, mY);
        cube.paintComponent(g2);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public void move(int x, int y) {
        mX = x;
        mY = y;
        this.shapeMoved = true;
    }

    @Override

    public boolean contains(int x, int y) {
        return false;
    }

    public void savePosition() {
        this.previousXPositions.add(mX);
        this.previousYPositions.add(mY);
    }

    public void restorePosition() {
        if (!previousXPositions.isEmpty() && !previousYPositions.isEmpty()) {
            mX = this.previousXPositions.remove(previousXPositions.size() - 1);
            mY = this.previousYPositions.remove(previousYPositions.size() - 1);
        }
    }
}