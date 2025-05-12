package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.clibraries.Directives;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GLUT;
import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.type.CIntPointer;

@CContext(Directives.class)
public class MouseListener {

    public static final CGlobalData<CIntPointer> mouseX
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CIntPointer> mouseY
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CEntryPointLiteral<GLUT.Callback2i> mouseMotionCallback
            = CEntryPointLiteral.create(MouseListener.class, "onMouseMotion", int.class, int.class);

    public static final CEntryPointLiteral<GLUT.Callback4i> mouseClickCallback
            = CEntryPointLiteral.create(MouseListener.class, "onMouseClick", int.class, int.class, int.class, int.class);

    @SuppressWarnings("unused") // implicitly called with this.mouseMotionCallback
    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
            epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    public static void onMouseMotion(int x, int y) {
        var xRotationPtr = OpenGLRenderer.xRotation.get();
        var yRotationPtr = OpenGLRenderer.yRotation.get();
        var mouseXPtr = mouseX.get();
        var mouseYPtr = mouseY.get();
        var deltaX = x - mouseXPtr.read();
        var deltaY = y - mouseYPtr.read();
        // rotiere entlang der Y-Achse, wenn sich Maus in X-Richtung bewegt
        xRotationPtr.write((deltaY + xRotationPtr.read() + 360) % 360);
        // rotiere entlang der X-Achse, wenn sich Maus in Y-Richtung bewegt
        yRotationPtr.write((deltaX + yRotationPtr.read() + 360) % 360);
        mouseXPtr.write(x);
        mouseYPtr.write(y);
    }

    @SuppressWarnings("unused") // implicitly called with this.mouseClickCallback
    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
            epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void onMouseClick(int button, int state, int x, int y) {
        var scalePtr = OpenGLRenderer.scale.get();

        if (button == 3 /* mouse wheel up */) {
            scalePtr.write(scalePtr.read() * 1.1f);
        } else if (button == 4 /* mouse wheel down */) {
            scalePtr.write(scalePtr.read() * 0.9f);
        }

        mouseX.get().write(x);
        mouseY.get().write(y);
    }
}
