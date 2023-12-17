import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.persistance.shapes.shapes.Circle;
import com.persistance.shapes.shapes.SimpleShape;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.uga.miage.m1.polygons.gui.DrawingPanel;

class DrawingPanelTest {

    @Test
    @DisplayName("Test de la méthode paintComponent")
    void testPaintComponent() {
        List<SimpleShape> listOfShapes = new ArrayList<>();
        listOfShapes.add(new Circle(100, 100));

        DrawingPanel panel = new DrawingPanel(listOfShapes);

        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        panel.paintComponent(g2);

        assertTrue(imageContainsDrawing(image));
    }

    private boolean imageContainsDrawing(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
