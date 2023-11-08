package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
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
public class XMLVisitor implements Visitor {

    private String representation = null;

    public XMLVisitor() {
        // empty pour le moment

    }

    @Override
    public void visit(Circle circle) {
        representation = generateShapeXML("circle", circle.getX(), circle.getY());
    }

    @Override
    public void visit(Square square) {
        representation = generateShapeXML("square", square.getX(), square.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        representation = generateShapeXML("triangle", triangle.getX(), triangle.getY());
    }

    private String generateShapeXML(String type, int x, int y) {
        return String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", type, x, y);
    }

}
