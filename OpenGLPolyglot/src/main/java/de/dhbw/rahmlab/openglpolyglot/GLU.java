package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;

@CContext(Directives.class)
public class GLU {
    @CFunction("gluOrtho2D")
    static native void ortho2D(double left, double right, double bottom, double top);
}
