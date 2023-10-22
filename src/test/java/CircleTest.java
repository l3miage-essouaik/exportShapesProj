import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class CircleTest {
    @Test
    @DisplayName("Test Circle get  ")
    void testCircleGetX() {
        Circle circle = new Circle(30, 35);

        assertEquals(5, circle.getX());
    }
    @Test
    @DisplayName("Test  getY")
    void testCircleGetY() {
        Circle circle = new Circle(30, 35);

        assertEquals(10, circle.getY());
    }
}
