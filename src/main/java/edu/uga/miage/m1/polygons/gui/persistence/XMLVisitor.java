package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.GroupeShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
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
    public void visit(GroupeShape groupeShape) {
        StringBuilder representation2 = new StringBuilder();
        representation2.append("{\n")
                .append("    \"type\": \"groupeShape\",\n")
                .append("    \"shapes\": [\n");

        for (SimpleShape g : groupeShape.shapesGroupe) {
            String gString = "";
            if (g instanceof Circle)
                gString = "circle";
            else if (g instanceof Triangle)
                gString = "triangle";

            else if (g instanceof Cube)
                gString = "cube";
            if (g instanceof Square)
                gString = "square";

            representation2.append("        ").append(generateShapeXML(gString, g.getX(), g.getY())).append(",\n");
        }
        if (!groupeShape.shapesGroupe.isEmpty()) {
            representation2.deleteCharAt(representation2.length() - 2);
        }
        representation2.append("    ]\n")
                .append("}");

        this.representation = representation2.toString();
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
    public void visit(Cube cube) {
        representation = generateShapeXML("cube", cube.getX(), cube.getY());
    }

    @Override
    public void visit(Triangle triangle) {
        representation = generateShapeXML("triangle", triangle.getX(), triangle.getY());
    }

    private String generateShapeXML(String type, int x, int y) {
        return String.format("<shape><type>%s</type><x>%d</x><y>%d</y></shape>", type, x, y);
    }

}
