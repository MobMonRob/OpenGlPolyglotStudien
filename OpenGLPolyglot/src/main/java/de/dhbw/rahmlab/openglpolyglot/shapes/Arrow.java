package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import de.dhbw.rahmlab.openglpolyglot.ShapeDrawingUtils;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Arrow implements Shape {

    private final Point3d location;
    private final Vector3d direction;
    private final double radius;
    private final Color color;

    public Arrow(Point3d location, Vector3d direction, double radius, Color color) {
        this.location = location;
        this.direction = direction;
        this.radius = radius;
        this.color = color;
    }
    
    public void draw() {
        // verschiebe Koordinatensystem, sodass location im Ursprung ist
        GL.translated(location.x, location.y, location.z);

        // rotiere Koordinatensystem, sodass z-Achse in Pfeilrichtung zeigt
        var rotationAngle = ShapeDrawingUtils.getAngleToZAxis(direction);
        GL.rotated(rotationAngle, -direction.y, direction.x, 0);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        var coneHeight = radius * 2.5;
        var cylinderHeight = ShapeDrawingUtils.getVectorLength(direction) - coneHeight;
        var coneRadius = radius*1.6;
        ShapeDrawingUtils.drawCylinder(radius, cylinderHeight);
        Circle.drawCircle(true, false, 20, radius);
        GL.translated(0, 0, cylinderHeight);
        ShapeDrawingUtils.drawCone(coneRadius, coneHeight);
        Circle.drawCircle(true, false, 20, coneRadius);

        GL.translated(0, 0, -cylinderHeight);
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }
}
