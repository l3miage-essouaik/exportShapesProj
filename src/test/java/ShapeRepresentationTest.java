import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;


 class ShapeRepresentationTest {

    @Test
    @DisplayName("Test JSONRepresentation Circle ")
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

    @Test
    @DisplayName("Test JSONRepresentation triangle ")
    void testJSONRepresentationTriangle() {
        Triangle triangle = new Triangle(30, 35);
        JSonVisitor visitor = new JSonVisitor();

        visitor.visit(triangle);

        String expected = """
{
    "type": "triangle",
    "x": 5,
    "y": 10
}""";

        String actual = visitor.getRepresentation();
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Test visit Circle xml")
    void testXmlVisitCircle() {
        Circle circle = new Circle(30, 35);
        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(circle);

        String expected = "<shape>"+
                "<type>circle</type>"+
                "<x>5</x>"+
                "<y>10</y>"+
                "</shape>";
        String actual = visitor.getRepresentation();
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test visit Square xml")
    void testXmlVisitSquare() {
        Square square = new Square(30,35);
        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(square);

        String expected = "<shape>"+
                "<type>square</type>"+
                "<x>5</x>"+
                "<y>10</y>"+
                "</shape>";
        String actual = visitor.getRepresentation();
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test visit Triangle xml")
    void testXmlVisitTriangle() {
        Triangle triangle = new Triangle(30,35);
        XMLVisitor visitor = new XMLVisitor();

        visitor.visit(triangle);

        String expected  = "<shape>"+
                "<type>triangle</type>"+
                "<x>5</x>"+
                "<y>10</y>"+
                "</shape>";
        String actual = visitor.getRepresentation();
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }
}
