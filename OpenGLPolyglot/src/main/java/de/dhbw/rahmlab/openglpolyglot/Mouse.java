package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.type.CIntPointer;

@CContext(Directives.class)
public class Mouse {

    private static final CGlobalData<CIntPointer> mouseX =
        CGlobalDataFactory.createBytes(() -> 4);

    private static final CGlobalData<CIntPointer> mouseY =
        CGlobalDataFactory.createBytes(() -> 4);

    public static final CEntryPointLiteral<GLUT.Callback2i> mouseMotionCallback =
        CEntryPointLiteral.create(Mouse.class, "onMouseMotion", int.class, int.class);

    public static final CEntryPointLiteral<GLUT.Callback4i> mouseClickCallback =
        CEntryPointLiteral.create(Mouse.class, "onMouseClick", int.class, int.class, int.class, int.class);

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void onMouseMotion(int x, int y) {
        var xRotationPtr = OpenGLPolyglot.xRotation.get();
        var yRotationPtr = OpenGLPolyglot.yRotation.get();
        var mouseXPtr = mouseX.get();
        var mouseYPtr = mouseY.get();
        var deltaX = x - mouseXPtr.read();
        var deltaY = y - mouseYPtr.read();
        // rotiere entlang der Y-Achse, wenn sich Maus in X-Richtung bewegt
        xRotationPtr.write(deltaY + xRotationPtr.read());
        // rotiere entlang der X-Achse, wenn sich Maus in Y-Richtung bewegt
        yRotationPtr.write(deltaX + yRotationPtr.read());
        mouseXPtr.write(x);
        mouseYPtr.write(y);
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void onMouseClick(int button, int state, int x, int y) {
        mouseX.get().write(x);
        mouseY.get().write(y);
    }
}