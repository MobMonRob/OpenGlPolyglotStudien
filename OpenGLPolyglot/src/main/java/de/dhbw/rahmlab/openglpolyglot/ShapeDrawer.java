package de.dhbw.rahmlab.openglpolyglot;

import java.awt.Color;
import org.jogamp.vecmath.Point3d;

public class ShapeDrawer {

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
    }
}
