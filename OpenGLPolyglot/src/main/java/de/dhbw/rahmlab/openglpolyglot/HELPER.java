package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.VoidPointer;

@CContext(Directives.class)
public class HELPER {
    @CFunction(transition = CFunction.Transition.NO_TRANSITION)
    public static native VoidPointer getGLUTBitmap9By15();
}
