package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.orat.view3d.euclid3dviewapi.api.ViewerService;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;

@CContext(Directives.class)
public class OpenGLPolyglot {

    public static final CGlobalData<CFloatPointer> xRotation =
        CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CFloatPointer> yRotation =
        CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CFloatPointer> scale =
        CGlobalDataFactory.createBytes(() -> 4);

    private static final CEntryPointLiteral<GLUT.Callback> displayCallback =
        CEntryPointLiteral.create(OpenGLPolyglot.class, "display");
    
    private static final CEntryPointLiteral<GLUT.Callback> idleCallback =
        CEntryPointLiteral.create(OpenGLPolyglot.class, "idle");
    
    private static final CEntryPointLiteral<GLUT.Callback2i> reshapeCallback =
        CEntryPointLiteral.create(OpenGLPolyglot.class, "reshape", int.class, int.class);

    public static EuclidViewer3D viewer;

    @CEntryPoint
    @CEntryPointOptions(prologue = IsolateSingleton.Prologue.class,
                        epilogue = CEntryPointSetup.LeaveEpilogue.class)
    public static void initialize() {
        viewer = (EuclidViewer3D) ViewerService.getInstance().getViewer().get();
        scale.get().write(1f);
        initializeWindow();
        setUpLightingAndMaterials();

        GLUT.displayFunc(displayCallback.getFunctionPointer());
        GLUT.idleFunc(idleCallback.getFunctionPointer());
        GLUT.reshapeFunc(reshapeCallback.getFunctionPointer());
        GLUT.mouseFunc(Mouse.mouseClickCallback.getFunctionPointer());
        GLUT.motionFunc(Mouse.mouseMotionCallback.getFunctionPointer());
        GLUT.mainLoop();
    }

    private static void initializeWindow() {
        try (var argv = CTypeConversion.toCStrings(new String[0])) {
            var argc = StackValue.get(CIntPointer.class);
            argc.write(0);
            GLUT.init(argc, argv.get());
        }

        GLUT.initDisplayMode(GLUT.SINGLE() | GLUT.RGB() | GLUT.DEPTH());
        GLUT.initWindowPosition(15, 15);
        GLUT.initWindowSize(800, 800);
        try (var title = CTypeConversion.toCString("GraalVM OpenGL")) {
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

        GL.blendFunc(GL.SRC_ALPHA(), GL.ONE_MINUS_SRC_ALPHA());
        GL.enable(GL.BLEND());
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = IsolateSingleton.Prologue.class,
                        epilogue = CEntryPointSetup.LeaveEpilogue.class)
    private static void display() {
        var scalef = scale.get().read();
 
        GL.clear(GL.COLOR_BUFFER_BIT() | GL.DEPTH_BUFFER_BIT());
        GL.loadIdentity();

        GL.scalef(scalef, scalef, scalef);
        GL.rotatef(xRotation.get().read(), 1f, 0f, 0f);
        GL.rotatef(yRotation.get().read(), 0f, 1f, 0f);
        /*try (var mat = PinnedObject.create(new float[] {1, 0, 0, 0})) {
            GL.materialfv(GL.FRONT(), GL.DIFFUSE(), mat.addressOfArrayElement(0));
        }*/

        Shape.drawAll(viewer.getNodes().values());

        GL.flush();
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void idle() {
        GLUT.postRedisplay();
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
    private static void reshape(int width, int height) {
        double ratio = (double) width / (double) height;
        GL.viewport(0, 0, width, height);
        GL.matrixMode(GL.PROJECTION());
        GL.loadIdentity();
        GL.ortho(-10 * ratio, 10 * ratio, -10, 10, -10, 10);
        GL.matrixMode(GL.MODELVIEW());
    }
}
