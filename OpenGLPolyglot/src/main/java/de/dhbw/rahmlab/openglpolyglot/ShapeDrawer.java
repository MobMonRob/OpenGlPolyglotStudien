package de.dhbw.rahmlab.openglpolyglot;

import java.awt.Color;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class ShapeDrawer {

    public static void drawSphere(Point3d location, double radius, Color color) {
        var quadric = GLU.newQuadric();
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());
        GL.translated(location.x, location.y, location.z);
        GLU.sphere(quadric, radius, 100, 20);
        GL.translated(-location.x, -location.y, -location.z);
    }

    public static void drawLine(Point3d p1, Point3d p2, Color color, float width) {
        GL.lineWidth(width);
        GL.begin(GL.LINES());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());
        GL.vertex3d(p1.x, p1.y, p1.z);
        GL.vertex3d(p2.x, p2.y, p2.z);
        GL.end();
        GL.lineWidth(1f);
    }

    public static void drawArrow(Point3d location, Vector3d direction,
            double radius, Color color) {

        // verschiebe Koordinatensystem, sodass location im Ursprung ist
        GL.translated(location.x, location.y, location.z);

        // rotiere Koordinatensystem, sodass z-Achse in Pfeilrichtung zeigt
        var rotationAngle = getAngleToZAxis(direction);
        GL.rotated(rotationAngle, -direction.y, direction.x, 0);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        var coneHeight = radius * 2.5;
        var cylinderHeight = getVectorLength(direction) - coneHeight;
        var coneRadius = radius*1.6;
        drawCylinder(radius, cylinderHeight);
        drawCircle(true, false, 20, radius);
        GL.translated(0, 0, cylinderHeight);
        drawCone(coneRadius, coneHeight);
        drawCircle(true, false, 20, coneRadius);

        GL.translated(0, 0, -cylinderHeight);
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }

    public static void drawCircle(Point3d location, Vector3d normal, int edges,
            double radius, Color color, boolean isDashed, boolean isFilled) {

        // verschiebe Koordinatensystem, sodass Kreismittelpunkt im Ursprung ist
        GL.translated(location.x, location.y, location.z);

        // rotiere Koordinatensystem, sodass Kreis auf xy-Ebene ist
        var rotationAngle = getAngleToZAxis(normal);
        GL.rotated(rotationAngle, -normal.y, normal.x, 0);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        drawCircle(isFilled, isDashed, edges, radius);

        GL.rotated(-rotationAngle, -normal.y, normal.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }

    private static void drawCircle(boolean isFilled, boolean isDashed, int edges, double radius) {
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

    public static void drawPolygon(Point3d[] corners, Color color) {
        GL.begin(GL.POLYGON());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        for (var corner : corners) {
            GL.vertex3d(corner.x, corner.y, corner.z);
        }

        GL.end();
    }

    public static void drawCube(Point3d location, double width, Color color) {
        var halfWidth = width / 2;

        GL.begin(GL.QUADS());
        // TODO: remove fake lighting when real lighting is implemented
        GL.color3f(color.getRed()/255f * 0.9f, color.getGreen()/255f * 0.9f, color.getBlue()/255f * 0.9f);

        // vorne
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z + halfWidth);

        // hinten
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z - halfWidth);

        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());

        // oben
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z - halfWidth);

        // unten
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z - halfWidth);

        // TODO: remove fake lighting when real lighting is implemented
        GL.color3f(color.getRed()/255f * 0.8f, color.getGreen()/255f * 0.8f, color.getBlue()/255f * 0.8f);

        // rechts
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y - halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x + halfWidth, location.y + halfWidth, location.z - halfWidth);

        // links
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z + halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y - halfWidth, location.z - halfWidth);
        GL.vertex3d(location.x - halfWidth, location.y + halfWidth, location.z - halfWidth);

        GL.end();
    }

    private static void drawCylinder(double radius, double height) {
        GLU.cylinder(GLU.newQuadric(), radius, radius, height, 20, 20);
    }

    private static void drawCone(double radius, double height) {
        GLU.cylinder(GLU.newQuadric(), radius, 0, height, 20, 20);
    }

    private static double getAngleToZAxis(Vector3d v) {
        return Math.toDegrees(Math.acos(v.z / getVectorLength(v)));
    }

    private static double getVectorLength(Vector3d v) {
        return Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
    }
}
