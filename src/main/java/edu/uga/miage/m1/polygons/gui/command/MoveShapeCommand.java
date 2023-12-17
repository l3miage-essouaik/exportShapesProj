package edu.uga.miage.m1.polygons.gui.command;

import com.persistance.shapes.shapes.SimpleShape;

public class MoveShapeCommand implements Command {
    private SimpleShape simpleShape;
    private int newX;
    private int newY;

    public MoveShapeCommand(SimpleShape simpleShape, int newX, int newY) {
        this.simpleShape = simpleShape;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        this.simpleShape.move(this.newX, this.newY);
    }

}
