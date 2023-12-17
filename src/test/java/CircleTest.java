import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.persistance.persistance.persistence.JSonVisitor;
import com.persistance.persistance.persistence.XMLVisitor;
import com.persistance.shapes.shapes.Circle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CircleTest {
    @Test
    @DisplayName("Test Circle get X")
    void testCircleGetX() {
        Circle circle = new Circle(30, 35);

        assertEquals(5, circle.getX());
    }

    @Test
    @DisplayName("Test  Circle getY")
    void testCircleGetY() {
        Circle circle = new Circle(30, 35);

        assertEquals(10, circle.getY());
    }

    @Test
    @DisplayName("Test accept() CircleXml ")
    void testAcceptXML() {
        Circle circle = new Circle(10, 10);
        XMLVisitor visitor = new XMLVisitor();
        circle.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "<shape><type>circle</type><x>-15</x><y>-15</y></shape>";
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("Test Circle move")
    void testCircleMove() {
        Circle circle = new Circle(30, 35);

        circle.move(50, 60);

        assertEquals(50, circle.getX());
        assertEquals(60, circle.getY());
    }

    @Test
    @DisplayName("Test Circle save and restore position")
    void testCircleSaveAndRestorePosition() {
        Circle circle = new Circle(30, 35);

        circle.move(50, 60);
        circle.savePosition();

        circle.move(70, 80);
        circle.restorePosition();

        assertEquals(50, circle.getX());
        assertEquals(60, circle.getY());
    }

    @Test
    @DisplayName("Test accept() CircleJson")
    void testAcceptJSON() {
        Circle circle = new Circle(5, 5);
        JSonVisitor visitor = new JSonVisitor();
        circle.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "{\n" +
                "    \"type\": \"circle\",\n" +
                "    \"x\": -20,\n" +
                "    \"y\": -20\n" +
                "}";
        assertEquals(expResult, result);
    }

}
