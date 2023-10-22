package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class UndoShapeCommand implements Command {
    private JDrawingFrame jDrawingFrame;

    public UndoShapeCommand(JDrawingFrame jDrawingFrame){
        this.jDrawingFrame = jDrawingFrame;
    }

    public void execute() {
        jDrawingFrame.undo();
    }


}
