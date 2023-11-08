package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddCircleCommand implements Command {
    private Circle circle;
    private List<SimpleShape> listOfShapes;

    public AddCircleCommand(Circle circle, List<SimpleShape> listOfShapes) {
        this.circle = circle;
        this.listOfShapes = listOfShapes;
    }

    @Override
    public void execute() {
        listOfShapes.add(circle);
    }



    
}
