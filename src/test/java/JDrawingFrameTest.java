import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class JDrawingFrameTest {
    @Test
    void testConstructor() {
        String frameName = "Test Frame";
        JDrawingFrame drawingFrame = new JDrawingFrame(frameName);

        assertNotNull(drawingFrame);
        assertEquals(frameName, drawingFrame.getTitle());
    }

}
