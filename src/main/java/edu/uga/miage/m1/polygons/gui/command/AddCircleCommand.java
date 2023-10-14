package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AddCircleCommand implements Command {
    private Circle circle;
    private List<SimpleShape> list_of_shapes;

    public AddCircleCommand(Circle circle, List<SimpleShape> list_of_shapes) {
        this.circle = circle;
        this.list_of_shapes = list_of_shapes;
    }

    @Override
    public void execute() {
        list_of_shapes.add(circle);
    }

    @Override
    public void undo() {
        list_of_shapes.remove(circle);
    }

    
}
