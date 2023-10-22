import edu.uga.miage.m1.polygons.gui.shapes.Square;
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
}
