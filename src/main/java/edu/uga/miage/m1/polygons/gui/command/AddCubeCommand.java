package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AddCubeCommand implements Command {
    private Cube cube;
    private List<SimpleShape> listOfShapes;

    @Override
    public void execute() {
        listOfShapes.add(cube);
    }

    public AddCubeCommand(Cube cube, List<SimpleShape> listOfShapes) {
        this.cube = cube;
        this.listOfShapes = listOfShapes;
    }

}
