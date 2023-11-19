package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class MoveShapeCommand implements Command {
    private SimpleShape simpleShape;
    private JDrawingFrame jDrawingFrame;
    private int newX;
    private int newY;

    public MoveShapeCommand(SimpleShape simpleShape, JDrawingFrame jDrawingFrame, int newX, int newY) {
        this.simpleShape = simpleShape;
        this.jDrawingFrame = jDrawingFrame;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        this.simpleShape.move(this.newX, this.newY);
    }

}
