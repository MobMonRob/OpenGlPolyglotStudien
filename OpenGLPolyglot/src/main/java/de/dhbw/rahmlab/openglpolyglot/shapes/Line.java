package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;

public class Line implements Shape {

    private final Point3d p1;
    private final Point3d p2;
    private final Color color;
    private final float width;

    public Line(Point3d p1, Point3d p2, Color color, float width) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.width = width;
    }
    
    public void draw() {
        GL.lineWidth(width);
        GL.begin(GL.LINES());
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());
        GL.vertex3d(p1.x, p1.y, p1.z);
        GL.vertex3d(p2.x, p2.y, p2.z);
        GL.end();
        GL.lineWidth(1f);
    }
}
