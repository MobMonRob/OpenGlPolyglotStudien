package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import java.awt.Color;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Circle implements Shape {

    private final Point3d location;
    private final Vector3d normal;
    private final int edges;
    private final double radius;
    private final Color color;
    private final String label;
    private final boolean isDashed;
    private final boolean isFilled;

    public Circle(Point3d location, Vector3d normal, int edges, double radius,
            Color color, String label, boolean isDashed, boolean isFilled) {
        this.location = location;
        this.normal = normal;
        this.edges = edges;
        this.radius = radius;
        this.color = color;
        this.label = label;
        this.isDashed = isDashed;
        this.isFilled = isFilled;
    }

    @Override
    public void draw() {
        // verschiebe Koordinatensystem, sodass Kreismittelpunkt im Ursprung ist
        GL.translated(location.x, location.y, location.z);

        // rotiere Koordinatensystem, sodass Kreis auf xy-Ebene ist
        var rotationAngle = ShapeDrawingUtils.getAngleToZAxis(normal);
        GL.rotated(rotationAngle, -normal.y, normal.x, 0);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        drawCircle(isFilled, isDashed, edges, radius);
        ShapeDrawingUtils.drawLabel(" " + label, new Vector3d(0, 0, 0.1));

        GL.rotated(-rotationAngle, -normal.y, normal.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }

    public static void drawCircle(boolean isFilled, boolean isDashed, int edges, double radius) {
        if (isFilled) {
            // zeichne Dreiecks-"Fächer" vom Mittelpunkt aus
            // (performanter als GL_POLYGON, da Grafikkarten für Dreiecke optimiert sind)
            GL.begin(GL.TRIANGLE_FAN());
            GL.vertex3d(0, 0, 0);
        } else if (isDashed) {
            GL.begin(GL.LINES());
        } else {
            GL.begin(GL.LINE_STRIP());
        }

        for (int i = 0; i <= edges; i++) {
            GL.vertex2d(radius * Math.cos(i * Math.TAU/edges),
                    radius * Math.sin(i * Math.TAU/edges));
        }

        GL.end();
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(location);
        transformMatrix.transform(normal);
    }
}
