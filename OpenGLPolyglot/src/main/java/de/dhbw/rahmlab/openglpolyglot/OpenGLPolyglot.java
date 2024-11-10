package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

@CContext(Directives.class)
public class OpenGLPolyglot {

    public static void main(String[] args) {
        initializeWindow(args);
        setUpLightingAndMaterials();

        GLUT.displayFunc(displayCallback.getFunctionPointer());
        GLUT.idleFunc(idleCallback.getFunctionPointer());
        GLUT.reshapeFunc(reshapeCallback.getFunctionPointer());
        GLUT.mainLoop();
    }

    private static void initializeWindow(String[] args) {
        try (var argv = CTypeConversion.toCStrings(args)) {
            var argc = StackValue.get(CIntPointer.class);
            argc.write(args.length);
            GLUT.init(argc, argv.get());
        }

        GLUT.initDisplayMode(GLUT.SINGLE() | GLUT.RGB() | GLUT.DEPTH());
        GLUT.initWindowPosition(15, 15);
        GLUT.initWindowSize(800, 800);
        try (var title = CTypeConversion.toCString("Utah Teapot - GraalVM")) {
            GLUT.createWindow(title.get());
        }
    }

    private static void setUpLightingAndMaterials() {
        GL.clearColor(1f, 1f, 1f, 1f);
        GL.shadeModel(GL.SMOOTH());
        /*try (var white = PinnedObject.create(new float[] {1f, 1f, 1f, 0f});
                var shine = PinnedObject.create(new float[] {70f})) {
            GL.lightfv(GL.LIGHT0(), GL.AMBIENT(), white.addressOfArrayElement(0));
            GL.lightfv(GL.LIGHT0(), GL.DIFFUSE(), white.addressOfArrayElement(0));
            GL.materialfv(GL.FRONT(), GL.SHININESS(), shine.addressOfArrayElement(0));
        }

        GL.enable(GL.LIGHTING());
        GL.enable(GL.LIGHT0());*/
        GL.enable(GL.DEPTH_TEST());
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void display() {
        GL.clear(GL.COLOR_BUFFER_BIT() | GL.DEPTH_BUFFER_BIT());
        GL.loadIdentity();

        GL.scalef(0.5f, 0.5f, 0.5f);
        GL.rotatef(rotation.get().read(), 0f, 1f, 1f);
        /*try (var mat = PinnedObject.create(new float[] {1, 0, 0, 0})) {
            GL.materialfv(GL.FRONT(), GL.DIFFUSE(), mat.addressOfArrayElement(0));
        }*/

        GL.begin(GL.QUADS());
        GL.color3f(1, 0, 0);

        // vorne
        GL.vertex3f(-1f, 1f, 1f);
        GL.vertex3f(-1f, -1f, 1f);
        GL.vertex3f(1f, -1f, 1f);
        GL.vertex3f(1f, 1f, 1f);

        // hinten
        GL.vertex3f(-1f, 1f, -1f);
        GL.vertex3f(-1f, -1f, -1f);
        GL.vertex3f(1f, -1f, -1f);
        GL.vertex3f(1f, 1f, -1f);

        GL.color3f(0, 1, 0);
        // oben
        GL.vertex3f(-1, 1, -1);
        GL.vertex3f(-1, 1, 1);
        GL.vertex3f(1, 1, 1);
        GL.vertex3f(1, 1, -1);
        
        // unten
        GL.vertex3f(-1, -1, -1);
        GL.vertex3f(-1, -1, 1);
        GL.vertex3f(1, -1, 1);
        GL.vertex3f(1, -1, -1);

        GL.color3f(0, 0, 1);
        // rechts
        GL.vertex3f(1, 1, 1);
        GL.vertex3f(1, -1, 1);
        GL.vertex3f(1, -1, -1);
        GL.vertex3f(1, 1, -1);

        // links
        GL.vertex3f(-1, 1, 1);
        GL.vertex3f(-1, -1, 1);
        GL.vertex3f(-1, -1, -1);
        GL.vertex3f(-1, 1, -1);

        GL.end();
        GL.flush();
    }

    private static final CEntryPointLiteral<GLUT.Callback> displayCallback =
        CEntryPointLiteral.create(OpenGLPolyglot.class, "display");

    private static final CGlobalData<CFloatPointer> rotation = 
        CGlobalDataFactory.createBytes(() -> 4);

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void idle() {
        var rotPtr = rotation.get();
        rotPtr.write(0.1f + rotPtr.read());
        GLUT.postRedisplay();
    }

    private static final CEntryPointLiteral<GLUT.Callback> idleCallback =
            CEntryPointLiteral.create(OpenGLPolyglot.class, "idle");

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void reshape(int width, int height) {
        double ratio = (double) width / (double) height;
        GL.viewport(0, 0, width, height);
        GL.matrixMode(GL.PROJECTION());
        GL.loadIdentity();
        GLU.ortho2D(-10 * ratio, 10 * ratio, -10, 10);
        GL.matrixMode(GL.MODELVIEW());
    }

    private static final CEntryPointLiteral<GLUT.Callback2i> reshapeCallback =
            CEntryPointLiteral.create(OpenGLPolyglot.class, "reshape", int.class, int.class);
}
