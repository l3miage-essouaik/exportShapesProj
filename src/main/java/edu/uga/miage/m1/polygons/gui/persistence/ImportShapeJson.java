package edu.uga.miage.m1.polygons.gui.persistence;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

public class ImportShapeJson {
    public List<SimpleShape> importShapesFromJSON(String filePath) {
        List<SimpleShape> shapes = new ArrayList<>();
        Gson gson = new Gson();

        try (Reader reader = new FileReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray shapesArray = jsonObject.getAsJsonArray("shapes");

            for (JsonElement shapeElement : shapesArray) {
                JsonObject shapeObject = shapeElement.getAsJsonObject().get("shape").getAsJsonObject();

                // Extraction des données (type, x, y)
                String type = shapeObject.get("type").getAsString();
                int x = shapeObject.get("x").getAsInt();
                int y = shapeObject.get("y").getAsInt();
                SimpleShape shape = createShapeFromData(type, x, y);
                shapes.add(shape);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shapes;
    }

    private SimpleShape createShapeFromData(String type, int x, int y) {
        switch (type) {
            case "circle":
                return new Circle(x, y);

            case "square":
                return new Square(x, y);

            case "triangle":
                return new Triangle(x, y);

            case "cube":
                return new Cube(x, y);

            default:
                break;
        }
        return null;
    }
}
