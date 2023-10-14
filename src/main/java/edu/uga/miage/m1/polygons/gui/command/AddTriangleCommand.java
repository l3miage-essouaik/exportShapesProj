
package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AddTriangleCommand implements Command {
    private Triangle triangle;
    private List<SimpleShape> list_of_shapes;

    public AddTriangleCommand(Triangle triangle, List<SimpleShape> list_of_shapes) {
        this.triangle = triangle;
        this.list_of_shapes = list_of_shapes;
    }

    @Override
    public void execute() {
        list_of_shapes.add(triangle);
    }

    @Override
    public void undo() {
        list_of_shapes.remove(triangle);
    }

    
}
