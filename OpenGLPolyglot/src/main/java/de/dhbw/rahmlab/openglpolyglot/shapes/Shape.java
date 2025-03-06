package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.AABB;
import java.util.Collection;
import org.jogamp.vecmath.Matrix4d;

public interface Shape {

    public void draw();

    public boolean isTransparent();

    public void transform(Matrix4d transformMatrix);

    public AABB getAABB();

    public static void drawAll(Collection<Shape> shapes) {
        shapes.stream()
            .sorted((s1, s2) -> Boolean.compare(s1.isTransparent(), s2.isTransparent()))
            .forEach(shape -> shape.draw());
    }
}
