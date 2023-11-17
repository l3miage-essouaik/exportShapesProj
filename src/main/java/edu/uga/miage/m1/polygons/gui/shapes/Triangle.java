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
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle implements SimpleShape, Visitable {

    int mX;

    int mY;

    public Triangle(int x, int y) {
        mX = x - 25;
        mY = y - 25;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(mX, mY, Color.GREEN, (float)mX + 50, mY, Color.WHITE);
        g2.setPaint(gradient);
        float[] xcoords = { mX + 25, mX, mX + 50 };
        float[] ycoords = { mY, mY + 50, mY + 50 };
        GeneralPath polygon = new GeneralPath(Path2D.WIND_EVEN_ODD, xcoords.length);
        polygon.moveTo((float) mX + 25, mY);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2.fill(polygon);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(polygon);
    }

    @Override
    public void accept(Visitor visitor) {
        // aaa
        visitor.visit(this);
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    @Override
    public void setX(int x) {
        this.mX = x;
    }

    @Override
    public void setY(int y) {
       this.mY = y;
    }

    @Override
    public boolean contains(int x, int y) {
        // Coordonnées des sommets du triangle
        int x1 = mX + 25;
        int y1 = mY;
        int x2 = mX;
        int y2 = mY + 50;
        int x3 = mX + 50;
        int y3 = mY + 50;
    
        // Calculer les aires des trois triangles formés par les points (x, y) et les sommets du triangle
        double area = 0.5 * Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
        double area1 = 0.5 * Math.abs(x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2));
        double area2 = 0.5 * Math.abs(x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y));
        double area3 = 0.5 * Math.abs(x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2));
    
        // La somme des aires des triangles formés par (x, y) doit être égale à l'aire du triangle d'origine
        return Math.abs(area1 + area2 + area3 - area) < 0.1; // Utilisez une petite marge pour traiter les erreurs d'arrondi
    }
    
}
