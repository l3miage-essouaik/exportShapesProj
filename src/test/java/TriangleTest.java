import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class TriangleTest {
    @Test
    @DisplayName("Test Triangle getX  ")
    void testTriangleGetX() {
        Triangle triangle = new Triangle(30, 35);

        assertEquals(5, triangle.getX());
    }
}
