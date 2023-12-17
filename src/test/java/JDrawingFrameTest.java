import static org.junit.jupiter.api.Assertions.*;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import org.junit.jupiter.api.DisplayName;

import java.io.File;

import org.junit.jupiter.api.Test;

class JDrawingFrameTest {
    @Test
    @DisplayName("Test FileName ")
    void testConstructor() {
        String frameName = "Test";
        JDrawingFrame drawingFrame = new JDrawingFrame(frameName);

        assertNotNull(drawingFrame);
        assertEquals(frameName, drawingFrame.getTitle());
    }

    @Test
    @DisplayName("Test FileJson exists ")
    void testExportToJSON() {
        JDrawingFrame drawingFrame = new JDrawingFrame("Test");

        drawingFrame.exportToJson();

        File jsonFile = new File("dessin.json");
        assertTrue(jsonFile.exists());
    }

    @Test
    @DisplayName("Test FileXml exists ")
    void testExportToXml() {
        JDrawingFrame drawingFrame = new JDrawingFrame("Test");

        drawingFrame.exportToXML();

        File xmlFile = new File("dessin.xml");
        assertTrue(xmlFile.exists());
    }

    @Test
    @DisplayName("Test adding shapes")
    void testAddShapes() {
        JDrawingFrame drawingFrame = new JDrawingFrame("Test");

        drawingFrame.addShape(JDrawingFrame.Shapes.SQUARE, null);

        assertEquals(0, drawingFrame.getListOfShapes().size());
    }

}
