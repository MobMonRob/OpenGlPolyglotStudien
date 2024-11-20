package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GLU;
import org.jogamp.vecmath.Vector3d;

public class ShapeDrawingUtils {

    public static void drawCylinder(double radius, double height) {
        GLU.cylinder(GLU.newQuadric(), radius, radius, height, 20, 20);
    }

    public static void drawCone(double radius, double height) {
        GLU.cylinder(GLU.newQuadric(), radius, 0, height, 20, 20);
    }

    public static double getAngleToZAxis(Vector3d v) {
        return Math.toDegrees(Math.acos(v.z / getVectorLength(v)));
    }

    public static double getVectorLength(Vector3d v) {
        return Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
    }
}
