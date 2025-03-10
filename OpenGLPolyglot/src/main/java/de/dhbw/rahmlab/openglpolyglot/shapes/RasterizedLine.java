package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.AABB;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import java.awt.Color;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;

/**
 * A line where the width is specified in pixels, using glLineWidth and GL_LINES
 */
public class RasterizedLine implements Shape {

    private final Point3d start;
    private final Point3d end;
    private final Color color;
    private final float width;

    public RasterizedLine(Point3d start, Point3d end, Color color, float width) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.width = width;
    }

    @Override
    public void draw() {
        GL.lineWidth(width);
        GL.begin(GL.LINES());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());
        GL.vertex3d(start.x, start.y, start.z);
        GL.vertex3d(end.x, end.y, end.z);
        GL.end();
        GL.lineWidth(1f);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(start);
        transformMatrix.transform(end);
    }

    @Override
    public AABB getAABB() {
        return new AABB(Math.min(start.x, end.x), Math.min(start.y, end.y), Math.min(start.z, end.z),
                        Math.max(start.x, end.x), Math.max(start.y, end.y), Math.max(start.z, end.z));
    }

    public Point3d getStart() {
        return start;
    }

    public Point3d getEnd() {
        return end;
    }
}
