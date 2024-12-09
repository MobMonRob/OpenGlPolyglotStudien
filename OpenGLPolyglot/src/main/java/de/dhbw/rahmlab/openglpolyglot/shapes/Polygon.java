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
    private final boolean transparency;

    public Polygon(Point3d location, Point3d[] corners, Color color, String label, boolean transparency) {
        this.location = location;
        this.corners = corners;
        this.color = color;
        this.label = label;
        this.transparency = transparency;
    }

    @Override
    public void draw() {
        GL.begin(GL.POLYGON());
        GL.color4ub(color.getRed(), color.getGreen(), color.getBlue(), transparency ? 127 : color.getAlpha());

        for (var corner : corners) {
            GL.vertex3d(corner.x, corner.y, corner.z);
        }

        GL.end();
        ShapeDrawingUtils.drawLabel(label, new Vector3d(location.x, location.y, location.z));
    }

    @Override
    public boolean isTransparent() {
        return transparency;
    }
}
