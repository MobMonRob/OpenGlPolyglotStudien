package de.dhbw.rahmlab.openglpolyglot;

import java.awt.Color;
import org.jogamp.vecmath.Point3d;

public class ShapeDrawer {

    public static void drawSphere(Point3d location, double radius, Color color) {
        var quadric = GLU.newQuadric();
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
}
