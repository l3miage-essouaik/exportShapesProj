
package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AddSquareCommand implements Command {
    private Square square;
    private List<SimpleShape> listOfShapes;

    public AddSquareCommand(Square square, List<SimpleShape> listOfShapes) {
        this.square = square;
        this.listOfShapes = listOfShapes;
    }

    @Override
    public void execute() {
        listOfShapes.add(square);
    }

    @Override
    public void undo() {
        listOfShapes.remove(square);
    }

    
}
