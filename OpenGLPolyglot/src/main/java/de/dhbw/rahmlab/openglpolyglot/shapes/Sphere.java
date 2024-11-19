package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.GL;
import de.dhbw.rahmlab.openglpolyglot.GLU;
import java.awt.Color;
import org.jogamp.vecmath.Point3d;

public class Sphere implements Shape {

    private final Point3d location;
    private final double radius;
    private final Color color;

    public Sphere(Point3d location, double radius, Color color) {
        this.location = location;
        this.radius = radius;
        this.color = color;
    }
    
    public void draw() {
        GL.color3ub(color.getRed(), color.getGreen(), color.getBlue());
        GL.translated(location.x, location.y, location.z);
        GLU.sphere(GLU.newQuadric(), radius, 100, 20);
        GL.translated(-location.x, -location.y, -location.z);
    }
}
