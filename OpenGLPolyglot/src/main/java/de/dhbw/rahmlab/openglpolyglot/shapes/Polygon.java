package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Polygon implements Shape {

    private final Point3d location;

    private final Point3d[] corners;
    private final Color color;
    private final String label;

    public Polygon(Point3d location, Point3d[] corners, Color color, String label) {
        this.location = location;
        this.corners = corners;
        this.color = color;
        this.label = label;
    }

    @Override
    public void draw() {
        GL.begin(GL.POLYGON());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        for (var corner : corners) {
            GL.vertex3d(corner.x, corner.y, corner.z);
        }

        GL.end();
        ShapeDrawingUtils.drawLabel(label, new Vector3d(location.x, location.y, location.z));
    }
}
