import com.persistance.persistance.persistence.*;
import com.persistance.shapes.shapes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareTest {
    @Test
    @DisplayName("Test Square get  ")
    void testSquareGetX() {
        Square square = new Square(30, 35);

        assertEquals(5, square.getX());
    }

    @Test
    @DisplayName("Test  getY")
    void testSquareGetY() {
        Square square = new Square(30, 35);

        assertEquals(10, square.getY());
    }

    @Test
    @DisplayName("Test accept() squareXml ")
    void testAcceptXML() {
        Square square = new Square(10, 10);
        XMLVisitor visitor = new XMLVisitor();
        square.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "<shape><type>square</type><x>-15</x><y>-15</y></shape>";
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("Test Square move")
    void testSquareMove() {
        Square square = new Square(30, 35);

        square.move(50, 60);

        assertEquals(50, square.getX());
        assertEquals(60, square.getY());
    }

    @Test
    @DisplayName("Test Square save and restore position")
    void testSquareSaveAndRestorePosition() {
        Square square = new Square(30, 35);

        square.move(50, 60);
        square.savePosition();

        square.move(70, 80);
        square.restorePosition();

        assertEquals(50, square.getX());
        assertEquals(60, square.getY());
    }

    @Test
    @DisplayName("Test accept() squareJson")
    void testAcceptJSON() {
        Square square = new Square(5, 5);
        JSonVisitor visitor = new JSonVisitor();
        square.accept(visitor);
        String result = visitor.getRepresentation();
        String expResult = "{\n" +
                "    \"type\": \"square\",\n" +
                "    \"x\": -20,\n" +
                "    \"y\": -20\n" +
                "}";
        assertEquals(expResult, result);
    }
}
