import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTest {
    @Test
    @DisplayName("Test Triangle ")
    void testJSONRepresentationCircle() {
        Circle circle = new Circle(30, 35);
        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(circle);

        String expected = """
{
    "type": "circle",
    "x": 5,
    "y": 10
}""";
        System.out.println(expected);
        String actual = visitor.getRepresentation();
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}
