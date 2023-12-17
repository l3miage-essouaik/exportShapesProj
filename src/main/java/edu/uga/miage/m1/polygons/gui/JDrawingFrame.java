package edu.uga.miage.m1.polygons.gui;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.logging.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.persistance.persistance.persistence.JSonVisitor;
import com.persistance.persistance.persistence.Visitable;
import com.persistance.persistance.persistence.XMLVisitor;
import com.persistance.shapes.shapes.Circle;
import com.persistance.shapes.shapes.Cube;
import com.persistance.shapes.shapes.GroupeShape;
import com.persistance.shapes.shapes.SimpleShape;
import com.persistance.shapes.shapes.Square;
import com.persistance.shapes.shapes.Triangle;

import edu.uga.miage.m1.polygons.gui.command.*;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 * 
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    public enum Shapes {
        SQUARE, TRIANGLE, CIRCLE, CUBE
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private final JToolBar mtoolbar;

    private Shapes mSelected;

    private final JLabel mLabel;
    private final transient XMLVisitor xmlVisitor;
    private final transient JSonVisitor jsonVisitor;
    private final DrawingPanel mPanel;
    private final transient ActionListener mReusableActionListener = new ShapeActionListener();

    private final List<SimpleShape> listOfShapes = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(JDrawingFrame.class.getName());
    private final transient List<Command> commandHistory = new ArrayList<>();

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);
    private final transient Command undoCommand = new UndoShapeCommand(this);

    private final transient CommandInvoker undoCommandInvoker = new CommandInvoker(undoCommand);
    private transient SimpleShape selectedShape;
    private transient List<SimpleShape> lastSelectedShape = new ArrayList<>();
    private transient List<SimpleShape> selectedShapes = new ArrayList<>();
    private transient JToggleButton selectButton;
    private transient GroupeShape groupe = null;
    private transient ActionListener mImportActionListener = new ImportActionListener();

    /**
     * Default constructor that populates the main window.
     * 
     * @param frameName est un String
     */
    public JDrawingFrame(String frameName) {
        super(frameName);

        // Instantiates components
        mtoolbar = new JToolBar("Toolbar");
        mPanel = new DrawingPanel(listOfShapes);
        mPanel.setBackground(Color.WHITE);
        mPanel.setLayout(null);
        mPanel.setMinimumSize(new Dimension(400, 400));
        mPanel.addMouseListener(this);
        mPanel.addMouseMotionListener(this);
        mLabel = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(mtoolbar, BorderLayout.NORTH);
        add(mPanel, BorderLayout.CENTER);
        add(mLabel, BorderLayout.SOUTH);
        // Square
        URL squareImageUrl = getClass().getResource("images/square.png");
        if (squareImageUrl != null) {

            addShape(Shapes.SQUARE, new ImageIcon(squareImageUrl));
        }
        // Triangle
        URL triangleImageUrl = getClass().getResource("images/triangle.png");
        if (triangleImageUrl != null) {
            addShape(Shapes.TRIANGLE, new ImageIcon(triangleImageUrl));
        }

        // Circle
        URL circleImageUrl = getClass().getResource("images/circle.png");
        if (circleImageUrl != null) {
            addShape(Shapes.CIRCLE, new ImageIcon(circleImageUrl));
        }

        // Cube
        URL cubeImageUrl = getClass().getResource("images/underc.png");
        if (cubeImageUrl != null) {
            addShape(Shapes.CUBE, new ImageIcon(cubeImageUrl));
        }

        // select button
        selectButton = new JToggleButton("Select");
        selectButton.addActionListener(input -> selectGroup());
        mtoolbar.add(selectButton);
        mtoolbar.validate();

        setPreferredSize(new Dimension(600, 600));
        jsonVisitor = new com.persistance.persistance.persistence.JSonVisitor();
        xmlVisitor = new com.persistance.persistance.persistence.XMLVisitor();
        // Ajout des boutons d'export
        JButton exportToXmlButton = new JButton("Export to XML");
        exportToXmlButton.addActionListener(e -> exportToXML());

        mtoolbar.add(exportToXmlButton);
        JButton exportToJsonButton = new JButton("Export to JSON");
        exportToJsonButton.addActionListener(e -> exportToJson());

        mtoolbar.add(exportToJsonButton);

        mtoolbar.add(exportToXmlButton);
        JButton ctrlZ = new JButton("Revoke");
        ctrlZ.addActionListener(e -> undoCommandInvoker.execute());
        mtoolbar.add(ctrlZ);

        createMenuBar("activeimport");
        // keyboardFocusManager pour savoir si les buttons sont préssés
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(a -> {
            if (a.getID() == KeyEvent.KEY_PRESSED
                    && a.getKeyCode() == KeyEvent.VK_Z
                    && (a.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                keyPressed(a);
            }
            return false;
        });

        addKeyListener(this);
    }

    // Barre de menu JSON
    private void createMenuBar(String name) {
        JMenuBar menuBar = new JMenuBar();

        // Créer le menu "Import" et ajouter un élément de menu
        JMenu importMenu = new JMenu("Import");

        JMenuItem importShapesItem = new JMenuItem("Import Shapes");

        importShapesItem.setActionCommand(name);
        importShapesItem.addActionListener(mImportActionListener); // Ajouter un ActionListener
        importMenu.add(importShapesItem);

        // Ajouter les menus à la barre de menu
        menuBar.add(importMenu);

        // Associer la barre de menu à la fenêtre principale (votre JFrame)
        setJMenuBar(menuBar);
    }

    private void importShapes() {
        com.persistance.persistance.persistence.ImportShapeJson shapeIO = new com.persistance.persistance.persistence.ImportShapeJson();
        try {
            List<SimpleShape> importedShapes = shapeIO.importShapesFromJSON("dessin.json");
            listOfShapes.clear();
            listOfShapes.addAll(importedShapes);
            redrawShapesList();
            JOptionPane.showMessageDialog(JDrawingFrame.this, "success");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(JDrawingFrame.this,
                    "failure");
        }
    }

    public void redrawShapesList() {
        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
        g2.setColor(Color.WHITE); // Clear the panel by painting it white
        g2.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());

        // Redraw the remaining shapes from the listOfShapes
        for (SimpleShape shape : listOfShapes) {
            shape.draw(g2);
        }
    }

    // Create a group of shapes if the button is selected
    private void selectGroup() {
        if (selectButton.isSelected()) {
            this.groupe = new GroupeShape(0, 0, new ArrayList<>());
        }
    }

    public List<SimpleShape> getListOfShapes() {
        return listOfShapes;
    }

    public void exportToXML() {
        try (FileWriter writer = new FileWriter("dessin.xml")) {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);
            xmlWriter.writeStartDocument();
            xmlWriter.writeCharacters("\n");
            xmlWriter.writeStartElement("dessin");

            for (SimpleShape shape : listOfShapes) {
                if (shape instanceof Visitable visitable) {
                    visitable.accept(xmlVisitor);
                    xmlWriter.writeCharacters("\n");
                    writer.write(xmlVisitor.getRepresentation());
                }
            }
            xmlWriter.writeCharacters("\n");
            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "erreur:", e);
        }
    }

    public void exportToJson() {
        try (FileWriter writer = new FileWriter("dessin.json")) {
            writer.write("{\n\t\"shapes\": [\n\t");
            Iterator<SimpleShape> iterator = listOfShapes.iterator();

            while (iterator.hasNext()) {
                SimpleShape shape = iterator.next();

                if (shape instanceof Visitable visitable) {
                    visitable.accept(jsonVisitor);
                    writer.write("\t\t{\n\t\t\t\"shape\": \n");
                    writer.write(jsonVisitor.getRepresentation());

                    if (iterator.hasNext()) {
                        writer.write("\n\t\t},\n");
                    } else {
                        writer.write("\n\t\t}\n");
                    }
                }
            }

            writer.write("\t]\n}");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "erreur:", e);
        }
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     * 
     * @param shape The name of the injected <tt>SimpleShape</tt>.
     * @param icon  The icon associated with the injected <tt>SimpleShape</tt>.
     */
    public void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        mButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mReusableActionListener);
        if (mSelected == null) {
            button.doClick();
        }
        mtoolbar.add(button);
        mtoolbar.validate();
        repaint();
    }

    // Paint the Shape
    public void drawShape(SimpleShape shape) {
        shape.draw((Graphics2D) mPanel.getGraphics());
        listOfShapes.add(shape);
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (mPanel.contains(evt.getX(), evt.getY())) {
            if (selectButton.isSelected()) {
                handleSelection(evt);
            } else {
                addNewShape(evt);
            }
        }
    }

    private void handleSelection(MouseEvent evt) {
        for (int i = listOfShapes.size() - 1; i >= 0; i--) {
            SimpleShape shape = listOfShapes.get(i);
            if (isMouseInsideShape(evt.getX(), evt.getY(), shape)) {
                selectShape(shape);
                break;
            }
        }
    }

    private boolean isMouseInsideShape(int x, int y, SimpleShape shape) {
        return (shape.getX() <= x && x <= shape.getX() + 50) &&
                (shape.getY() <= y && y <= shape.getY() + 50);
    }

    private void selectShape(SimpleShape shape) {
        selectedShape = shape;
        if (groupe.getShapesGroupe().isEmpty()) {
            groupe.setXandY(selectedShape.getX(), selectedShape.getY());
        }
        selectedShape.setLastXY(selectedShape.getX() - groupe.getX(),
                selectedShape.getY() - groupe.getY());
        groupe.getShapesGroupe().add(selectedShape);
    }

    private void addNewShape(MouseEvent evt) {
        Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
        SimpleShape simpleShape = createShape(evt);
        if (simpleShape != null) {
            executeAddShape(g2, simpleShape, listOfShapes);
        }
    }

    private SimpleShape createShape(MouseEvent evt) {
        switch (mSelected) {
            case CIRCLE:
                return new Circle(evt.getX(), evt.getY());
            case TRIANGLE:
                return new Triangle(evt.getX(), evt.getY());
            case SQUARE:
                return new Square(evt.getX(), evt.getY());
            case CUBE:
                return new Cube(evt.getX(), evt.getY());
            default:
                return null;
        }
    }

    // execute addshapeCommand et savePosition
    public void executeAddShape(Graphics2D g2, SimpleShape simpleShape, List<SimpleShape> listOfShapes) {
        simpleShape.draw(g2);
        Command addShapeCommand = new AddShapeCommand(simpleShape, listOfShapes);
        addShapeCommand.execute();
        commandHistory.add(addShapeCommand);
        simpleShape.savePosition();
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseEntered(MouseEvent evt) {
        // empty pour le moment
    }

    /**
     * Implements an empty method for the <tt>MouseListener</tt> interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseExited(MouseEvent evt) {
        mLabel.setText(" ");
        mLabel.repaint();
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to initiate
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        int mouseX = evt.getX();
        int mouseY = evt.getY();

        for (SimpleShape shape : listOfShapes) {
            int shapeX = shape.getX();
            int shapeY = shape.getY();

            if (mouseX >= shapeX && mouseX <= (shapeX + 50) && mouseY >= shapeY && mouseY <= (shapeY + 50)) {
                selectedShape = shape;
                shape.savePosition();
                lastSelectedShape.add(shape);
                selectedShapes.add(shape);
                break;
            }
        }
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent evt) {
        selectedShape = null;
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * 
     * @param evt The associated mouse event.
     */
    @Override
    public void mouseDragged(MouseEvent evt) {
        if (selectedShape != null) {
            int newX = evt.getX() - 25;
            int newY = evt.getY() - 25;

            selectedShape.move(newX, newY);
            if (selectButton.isSelected()) {
                groupe.moveGroupe(newX, newY);
            }
            repaint();
        }
    }

    /**
     * Implements an empty method for the <tt>MouseMotionListener</tt>
     * interface.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
    }

    private void modifyLabel(MouseEvent evt) {
        mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape toolbar buttons that sets
     * the drawing frame's currently selected shape when receiving
     * an action event.
     */
    private class ShapeActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            // Itère sur tous les boutons
            for (Map.Entry<Shapes, JButton> entry : mButtons.entrySet()) {
                Shapes shape = entry.getKey();
                JButton btn = entry.getValue();
                if (evt.getActionCommand().equals(shape.toString())) {
                    btn.setBorderPainted(true);
                    mSelected = shape;
                } else {
                    btn.setBorderPainted(false);
                }
                btn.repaint();
            }

        }
    }

    private class ImportActionListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            if (evt.getActionCommand().equals("activeimport")) {
                importShapes();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            undoCommandInvoker.execute();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // empty pour le moment
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // empty pour le moment
    }

    @Override
    public void paintComponents(Graphics g) {
        Graphics2D newGraph = (Graphics2D) mPanel.getGraphics();
        newGraph.setColor(Color.WHITE);
        newGraph.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
        for (SimpleShape simpleShape : listOfShapes) {
            simpleShape.draw((Graphics2D) this.mPanel.getGraphics());
        }
    }

    private void handleShape(SimpleShape shape) {
        if (shape instanceof Circle circle) {
            handleCircle(circle);
        } else if (shape instanceof Triangle triangle) {
            handleTriangle(triangle);
        } else if (shape instanceof Square s) {
            handleSquare(s);
        } else if (shape instanceof Cube c) {
            handleCube(c);
        }
    }

    private void handleCircle(Circle circle) {
        circle.restorePosition();
        repaint();

        if (circle.getPreviousXPositions().size() == 1 && circle.getPreviousYPositions().size() == 1) {
            lastSelectedShape.remove(circle);
        }
    }

    private void handleSquare(Square square) {
        square.restorePosition();
        repaint();

        if (square.getPreviousXPositions().size() == 1 && square.getPreviousYPositions().size() == 1) {
            lastSelectedShape.remove(square);
        }
    }

    private void handleTriangle(Triangle triangle) {
        triangle.restorePosition();
        repaint();

        if (triangle.getPreviousXPositions().size() == 1 && triangle.getPreviousYPositions().size() == 1) {
            lastSelectedShape.remove(triangle);
        }
    }

    private void handleCube(Cube cube) {
        cube.restorePosition();
        repaint();

        if (cube.getPreviousXPositions().size() == 1 && cube.getPreviousYPositions().size() == 1) {
            lastSelectedShape.remove(cube);
        }
    }

    public void undoShape() {
        if (!commandHistory.isEmpty() && !listOfShapes.isEmpty() && this.lastSelectedShape.isEmpty()) {
            commandHistory.remove(commandHistory.size() - 1);
            listOfShapes.remove(listOfShapes.size() - 1);
        } else if (!this.lastSelectedShape.isEmpty()) {
            SimpleShape shape = lastSelectedShape.get(lastSelectedShape.size() - 1);
            handleShape(shape);
        }
        paintComponents(getGraphics());
    }

}
