package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.clibraries.FTGL;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GLU;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.jogamp.vecmath.Vector3d;

/**
 * Helper class providing common methods for shape drawing
 */
public class ShapeDrawingUtils {

    private static FTGL.Font font;

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
        FTGL.renderFont(getFont(), CTypeConversion.toCString(text).get(), FTGL.RENDER_ALL());
    }

    private static FTGL.Font getFont() {

        if (font.isNull()) {
            var fontPath = CTypeConversion.toCString("src/main/resources/Ubuntu-Medium.ttf");
            font = FTGL.createPixmapFont(fontPath.get());
            FTGL.setFontFaceSize(font, 10, 10);
        }

        return font;
    }
}
