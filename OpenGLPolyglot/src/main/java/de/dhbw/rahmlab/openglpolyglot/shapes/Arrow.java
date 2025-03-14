package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.AABB;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import java.awt.Color;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Arrow implements Shape {

    private final Point3d location;
    private final Vector3d direction;
    private final double radius;
    private final Color color;
    private final String label;

    public Arrow(Point3d location, Vector3d direction, double radius, Color color, String label) {
        this.location = location;
        this.direction = direction;
        this.radius = radius;
        this.color = color;
        this.label = label;
    }

    @Override
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

        GL.translated(0, 0, -cylinderHeight/2);
        ShapeDrawingUtils.drawLabel(" " + label);

        GL.translated(0, 0, -cylinderHeight/2);
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(location);
        transformMatrix.transform(direction);
    }

    @Override
    public AABB getAABB() { // approximation
        var coneRadius = radius*1.6;
        var start = location;
        var end = (Point3d) location.clone();
        end.add(direction);

        return new AABB(Math.min(start.x, end.x) - coneRadius, Math.min(start.y, end.y) - coneRadius, Math.min(start.z, end.z) - coneRadius,
                        Math.max(start.x, end.x) + coneRadius, Math.max(start.y, end.y) + coneRadius, Math.max(start.z, end.z) + coneRadius);
    }
}
