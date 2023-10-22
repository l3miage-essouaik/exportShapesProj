package test;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Visitortest {

    @Test
    @DisplayName("Test visit Circle")
    void testJsonVisitCircle() {
        Circle circle = new Circle(25, 30);
        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(circle);

        String expected = "\t{" +
                "\t\"type\" : \"circle\"," +
                "\t\"x\" : 0," +
                "\t\"y\" : 5}";
        String actual = visitor.getRepresentation();
        assertEquals(expected, actual);
    }
}
