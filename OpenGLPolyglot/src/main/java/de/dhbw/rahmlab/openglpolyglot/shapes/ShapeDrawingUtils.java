package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import de.dhbw.rahmlab.openglpolyglot.GLU;
import de.dhbw.rahmlab.openglpolyglot.GLUT;
import de.dhbw.rahmlab.openglpolyglot.HELPER;
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

    public static void drawLabel(String text) {
        drawLabel(text, new Vector3d(0, 0, 0));
    }

    public static void drawLabel(String text, Vector3d offset) {
        GL.color3i(0, 0, 0);
        GL.rasterPos3d(offset.x, offset.y, offset.z);

        for(var character : text.toCharArray()) {
            GLUT.bitmapCharacter(HELPER.getGLUTBitmap9By15(), character);
        }
    }
}
