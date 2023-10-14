
package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AddSquareCommand implements Command {
    private Square square;
    private List<SimpleShape> list_of_shapes;

    public AddSquareCommand(Square square, List<SimpleShape> list_of_shapes) {
        this.square = square;
        this.list_of_shapes = list_of_shapes;
    }

    @Override
    public void execute() {
        list_of_shapes.add(square);
    }

    @Override
    public void undo() {
        list_of_shapes.remove(square);
    }

    
}
