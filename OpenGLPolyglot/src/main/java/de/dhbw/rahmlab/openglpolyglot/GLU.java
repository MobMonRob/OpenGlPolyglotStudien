package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.word.PointerBase;

@CContext(Directives.class)
public class GLU {
    @CFunction("gluOrtho2D")
    static native void ortho2D(double left, double right, double bottom, double top);

    @CFunction("gluNewQuadric")
    static native Quadric newQuadric();

    @CFunction("gluSphere")
    static native void sphere(Quadric quad, double radius, int slices, int stacks);

    @CFunction("gluCylinder")
    static native void cylinder(Quadric quad, double base, double top, double height, int slices, int stacks);

    interface Quadric extends PointerBase {}
}
