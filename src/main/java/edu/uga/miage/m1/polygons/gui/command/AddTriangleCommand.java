
package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import com.persistance.shapes.shapes.SimpleShape;
import com.persistance.shapes.shapes.Triangle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTriangleCommand implements Command {
    private Triangle triangle;
    private List<SimpleShape> listOfShapes;

    public AddTriangleCommand(Triangle triangle, List<SimpleShape> listOfShapes) {
        this.triangle = triangle;
        this.listOfShapes = listOfShapes;
    }

    @Override
    public void execute() {
        listOfShapes.add(triangle);
    }

}
