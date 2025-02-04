package de.dhbw.rahmlab.openglpolyglot.clibraries;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.word.PointerBase;

@CContext(Directives.class)
public class GLU {
    @CFunction("gluOrtho2D")
    public static native void ortho2D(double left, double right, double bottom, double top);

    @CFunction("gluNewQuadric")
    public static native Quadric newQuadric();

    @CFunction("gluSphere")
    public static native void sphere(Quadric quad, double radius, int slices, int stacks);

    @CFunction("gluCylinder")
    public static native void cylinder(Quadric quad, double base, double top, double height, int slices, int stacks);

    public interface Quadric extends PointerBase {}
}
