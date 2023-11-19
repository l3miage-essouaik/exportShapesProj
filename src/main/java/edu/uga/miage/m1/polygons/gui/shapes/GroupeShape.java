package edu.uga.miage.m1.polygons.gui.shapes;

import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupeShape implements Visitable {
    public List<SimpleShape> shapesGroupe;
    int x;
    int y;

    public GroupeShape(int x, int y, List<SimpleShape> groupe) {
        this.x = x;
        this.y = y;
        this.shapesGroupe = new ArrayList<SimpleShape>();
        this.shapesGroupe = groupe;
    }

    public void moveElem() {
        for (SimpleShape shape : shapesGroupe) {
            shape.move(this.x + shape.getLastX(), this.y + shape.getLastY());
        }
    }

    public void moveGroupe(int x, int y) {
        this.x = x;
        this.y = y;
        moveElem();
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
