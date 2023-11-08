import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {
    @Test
    @DisplayName("Test Triangle getX  ")
    void testTriangleGetX() {
        Triangle triangle = new Triangle(30, 35);
        int x = triangle.getX();
        assertEquals(5, x);
    }

    @Test
    @DisplayName("Test  getY")
    void testTriangleGetY() {
        Triangle triangle = new Triangle(30, 35);

        assertEquals(10, triangle.getY());

    }

    @Test
    @DisplayName("Test accept() TriangleXml ")
    void testAcceptXML() {
        Triangle triangle = new Triangle(10, 10);
        XMLVisitor visitor = new XMLVisitor();
        triangle.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "<shape><type>triangle</type><x>-15</x><y>-15</y></shape>";
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("Test accept() TriangleJson")
    void testAcceptJSON() {
        Triangle triangle = new Triangle(5, 5);
        JSonVisitor visitor = new JSonVisitor();
        triangle.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "{\n" +
                "    \"type\": \"triangle\",\n" +
                "    \"x\": -20,\n" +
                "    \"y\": -20\n" +
                "}";
        assertEquals(expResult, result);
    }
}
