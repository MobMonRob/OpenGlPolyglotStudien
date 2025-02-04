package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import java.awt.Color;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class Cube implements Shape {

    private final Point3d location;
    private final Vector3d direction;
    private final double width;
    private final Color color;
    private final String label;
    private final boolean isTransparent;

    public Cube(Point3d location, Vector3d direction, double width, Color color, String label, boolean transparency) {
        this.location = location;
        this.direction = direction;
        this.width = width;
        this.color = color;
        this.label = label;
        this.isTransparent = transparency;
    }

    @Override
    public void draw() {
        var halfWidth = width / 2;
        var rotationAngle = ShapeDrawingUtils.getAngleToZAxis(direction);

        GL.translated(location.x, location.y, location.z);
        GL.rotated(rotationAngle, -direction.y, direction.x, 0);

        GL.begin(GL.QUADS());
        // TODO: remove fake lighting when real lighting is implemented
        GL.color4f(color.getRed()/255f * 0.9f, color.getGreen()/255f * 0.9f, color.getBlue()/255f * 0.9f, isTransparent ? 0.5f : color.getAlpha());

        // vorne
        GL.vertex3d(-halfWidth, +halfWidth, +halfWidth);
        GL.vertex3d(-halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, +halfWidth, +halfWidth);

        // hinten
        GL.vertex3d(-halfWidth, +halfWidth, -halfWidth);
        GL.vertex3d(-halfWidth, -halfWidth, -halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, -halfWidth);
        GL.vertex3d(+halfWidth, +halfWidth, -halfWidth);

        GL.color4ub(color.getRed(), color.getGreen(), color.getBlue(), isTransparent ? 127 : color.getAlpha());

        // oben
        GL.vertex3d(-halfWidth, +halfWidth, -halfWidth);
        GL.vertex3d(-halfWidth, +halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, +halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, +halfWidth, -halfWidth);

        // unten
        GL.vertex3d(-halfWidth, -halfWidth, -halfWidth);
        GL.vertex3d(-halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, -halfWidth);

        // TODO: remove fake lighting when real lighting is implemented
        GL.color4f(color.getRed()/255f * 0.8f, color.getGreen()/255f * 0.8f, color.getBlue()/255f * 0.8f, isTransparent ? 0.5f : color.getAlpha());

        // rechts
        GL.vertex3d(+halfWidth, +halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(+halfWidth, -halfWidth, -halfWidth);
        GL.vertex3d(+halfWidth, +halfWidth, -halfWidth);

        // links
        GL.vertex3d(-halfWidth, +halfWidth, +halfWidth);
        GL.vertex3d(-halfWidth, -halfWidth, +halfWidth);
        GL.vertex3d(-halfWidth, -halfWidth, -halfWidth);
        GL.vertex3d(-halfWidth, +halfWidth, -halfWidth);

        GL.end();
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        ShapeDrawingUtils.drawLabel(label, new Vector3d(0, halfWidth + 0.2, 0));
        GL.translated(-location.x, -location.y, -location.z);
    }

    @Override
    public boolean isTransparent() {
        return isTransparent;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(location);
        transformMatrix.transform(direction);
    }
}
