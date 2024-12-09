
package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Cylinder implements Shape {

    private final Point3d start;
    private final Point3d end;
    private final Color color;
    private final double radius;
    private final String label;

    public Cylinder(Point3d start, Point3d end, Color color, double radius, String label) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.radius = radius;
        this.label = label;
    }

    @Override
    public void draw() {
        // verschiebe Koordinatensystem, sodass 'start' im Ursprung ist
        GL.translated(start.x, start.y, start.z);

        // rotiere Koordinatensystem, sodass 'start' und 'end' auf z-Achse liegen
        var direction = new Vector3d(end.x - start.x, end.y - start.y, end.z - start.z);
        var rotationAngle = ShapeDrawingUtils.getAngleToZAxis(direction);
        GL.rotated(rotationAngle, -direction.y, direction.x, 0);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        var height = ShapeDrawingUtils.getVectorLength(direction);
        ShapeDrawingUtils.drawCylinder(radius, height); // Mantelfläche
        Circle.drawCircle(true, false, 20, radius);     // Kreisfläche bei 'start'
        GL.translated(0, 0, height);
        Circle.drawCircle(true, false, 20, radius);     // Kreisfläche bei 'end'

        GL.translated(0, 0, -height/2);
        ShapeDrawingUtils.drawLabel(" " + label);

        GL.translated(0, 0, -height/2);
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        GL.translated(-start.x, -start.y, -start.z);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
