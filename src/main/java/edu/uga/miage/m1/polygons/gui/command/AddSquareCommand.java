
package edu.uga.miage.m1.polygons.gui.command;

import java.util.List;

import com.persistance.shapes.shapes.SimpleShape;
import com.persistance.shapes.shapes.Square;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
