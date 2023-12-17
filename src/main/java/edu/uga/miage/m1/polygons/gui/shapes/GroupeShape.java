package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupeShape {
    private List<SimpleShape> shapesGroupe;
    int x;
    int y;

    public GroupeShape(int x, int y, List<SimpleShape> groupe) {
        this.x = x;
        this.y = y;
        this.shapesGroupe = new ArrayList<>();
        this.shapesGroupe = groupe;
    }

    public void moveShapes() {
        for (SimpleShape shape : shapesGroupe) {
            shape.move(this.x + shape.getLastX(), this.y + shape.getLastY());
        }
    }

    public void moveGroupe(int x, int y) {
        this.x = x;
        this.y = y;
        moveShapes();
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
