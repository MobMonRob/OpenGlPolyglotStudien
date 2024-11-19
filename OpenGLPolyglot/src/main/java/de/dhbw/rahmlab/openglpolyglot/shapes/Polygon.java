package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;

public class Polygon implements Shape {

    private final Point3d[] corners;
    private final Color color;

    public Polygon(Point3d[] corners, Color color) {
        this.corners = corners;
        this.color = color;
    }
    
    public void draw() {
        GL.begin(GL.POLYGON());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        for (var corner : corners) {
            GL.vertex3d(corner.x, corner.y, corner.z);
        }

        GL.end();
    }
}
