package de.dhbw.rahmlab.openglpolyglot.shapes;

import java.util.Collection;

public interface Shape {

    public void draw();

    public boolean isTransparent();

    public static void drawAll(Collection<Shape> shapes) {
        shapes.stream()
            .sorted((s1, s2) -> Boolean.compare(s1.isTransparent(), s2.isTransparent()))
            .forEach(shape -> shape.draw());
    }
}
