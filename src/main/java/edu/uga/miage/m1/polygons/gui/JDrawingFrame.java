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
import java.util.logging.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
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
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import edu.uga.miage.m1.polygons.gui.command.AddCircleCommand;
import edu.uga.miage.m1.polygons.gui.command.AddSquareCommand;
import edu.uga.miage.m1.polygons.gui.command.AddTriangleCommand;
import edu.uga.miage.m1.polygons.gui.command.Command;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 * 
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

    private enum Shapes {
        SQUARE, TRIANGLE, CIRCLE
    }

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
    private transient List<Command> commandHistory = new ArrayList<>();

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> mButtons = new EnumMap<>(Shapes.class);

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

        setPreferredSize(new Dimension(450, 450));
        jsonVisitor = new JSonVisitor();
        xmlVisitor = new XMLVisitor();
        // Ajout des boutons d'export
        JButton exportToXmlButton = new JButton("Export to XML");
        exportToXmlButton.addActionListener(e -> exportToXML());

        mtoolbar.add(exportToXmlButton);
        JButton exportToJsonButton = new JButton("Export to JSON");
        exportToJsonButton.addActionListener(e -> exportToJson());

        mtoolbar.add(exportToJsonButton);

        mtoolbar.add(exportToXmlButton);
        JButton ctrlZ = new JButton("Revoke");
        ctrlZ.addActionListener(e -> undo());
        mtoolbar.add(ctrlZ);

        addKeyListener(this);
    }

    private void exportToXML() {
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


    private void exportToJson() {
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
    private void addShape(Shapes shape, ImageIcon icon) {
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

    /**
     * Implements method for the <tt>MouseListener</tt> interface to
     * draw the selected shape into the drawing canvas.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseClicked(MouseEvent evt) {
        if (mPanel.contains(evt.getX(), evt.getY())) {
            Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
            switch (mSelected) {
                case CIRCLE:
                    Circle crc = new Circle(evt.getX(), evt.getY());
                    crc.draw(g2);
                    Command addCircleCommand = new AddCircleCommand(crc, listOfShapes);
                    addCircleCommand.execute();
                    commandHistory.add(addCircleCommand);
                    break;
                case TRIANGLE:
                    Triangle trg = new Triangle(evt.getX(), evt.getY());
                    trg.draw(g2);
                    Command addTriangleCommand = new AddTriangleCommand(trg, listOfShapes);
                    addTriangleCommand.execute();
                    commandHistory.add(addTriangleCommand);
                    break;
                case SQUARE:
                    Square sqr = new Square(evt.getX(), evt.getY());
                    sqr.draw(g2);
                    Command addSquareCommand = new AddSquareCommand(sqr, listOfShapes);
                    addSquareCommand.execute();
                    commandHistory.add(addSquareCommand);
                    break;
                default:
            }
        }
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
    public void mousePressed(MouseEvent evt) {
        // empty pour le moment
    }

    /**
     * Implements method for the <tt>MouseListener</tt> interface to complete
     * shape dragging.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseReleased(MouseEvent evt) {
        // empty pour le moment
    }

    /**
     * Implements method for the <tt>MouseMotionListener</tt> interface to
     * move a dragged shape.
     * 
     * @param evt The associated mouse event.
     */
    public void mouseDragged(MouseEvent evt) {
        // empty pour le moment

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

    @Override
    public void keyPressed(KeyEvent e) {
        mPanel.requestFocusInWindow();
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            // empty pour le moment
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

    private void undo() {
        if (!commandHistory.isEmpty()) {
            Command undoCommand = commandHistory.remove(commandHistory.size() - 1);
            undoCommand.undo();
            mPanel.repaint(); // Redessiner le panneau après la suppression
        }
    }

}
