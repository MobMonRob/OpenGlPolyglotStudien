package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import de.dhbw.rahmlab.openglpolyglot.GLU;
import java.awt.Color;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Sphere implements Shape {

    private final Point3d location;
    private final double radius;
    private final Color color;
    private final String label;
    private final boolean isTransparent;

    public Sphere(Point3d location, double radius, Color color, String label, boolean transparency) {
        this.location = location;
        this.radius = radius;
        this.color = color;
        this.label = label;
        this.isTransparent = transparency;
    }

    @Override
    public void draw() {
        GL.color4ub(color.getRed(), color.getGreen(), color.getBlue(), isTransparent ? 127 : color.getAlpha());
        GL.translated(location.x, location.y, location.z);
        GLU.sphere(GLU.newQuadric(), radius, 100, 20);
        ShapeDrawingUtils.drawLabel(label, new Vector3d(0, radius + 0.1, 0));
        GL.translated(-location.x, -location.y, -location.z);
    }

    @Override
    public boolean isTransparent() {
        return isTransparent;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(location);
    }
}
