package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */

@Getter
@Setter
public class JSonVisitor implements Visitor {

    private String representation = null;

    public JSonVisitor() {
        // empty pour le moment

    }

    @Override
    public void visit(Circle circle) {
        this.representation = generateJSON("circle", circle.getX(), circle.getY());
    }

    @Override
    public void visit(Square square) {
        this.representation = generateJSON("square", square.getX(), square.getY());
    }

    @Override
    public void visit(Cube cube) {
        this.representation = generateJSON("cube", cube.getX(), cube.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        this.representation = generateJSON("triangle", triangle.getX(), triangle.getY());
    }

    // logique de génération de de Json pour les 3 figures
    private String generateJSON(String type, int x, int y) {
        return "{\n" +
                "    \"type\": \"" + type + "\",\n" +
                "    \"x\": " + x + ",\n" +
                "    \"y\": " + y + "\n" +
                "}";
    }

}
