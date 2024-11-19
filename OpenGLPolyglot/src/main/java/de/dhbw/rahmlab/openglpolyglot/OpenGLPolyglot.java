package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import de.dhbw.rahmlab.openglpolyglot.shapes.Arrow;
import de.dhbw.rahmlab.openglpolyglot.shapes.Circle;
import de.dhbw.rahmlab.openglpolyglot.shapes.Cube;
import de.dhbw.rahmlab.openglpolyglot.shapes.Line;
import de.dhbw.rahmlab.openglpolyglot.shapes.Polygon;
import de.dhbw.rahmlab.openglpolyglot.shapes.Sphere;
import java.awt.Color;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.function.CEntryPointLiteral;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

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

    public static void main(String[] args) {
        scale.get().write(1f);
        initializeWindow(args);
        setUpLightingAndMaterials();

        GLUT.displayFunc(displayCallback.getFunctionPointer());
        GLUT.idleFunc(idleCallback.getFunctionPointer());
        GLUT.reshapeFunc(reshapeCallback.getFunctionPointer());
        GLUT.mouseFunc(Mouse.mouseClickCallback.getFunctionPointer());
        GLUT.motionFunc(Mouse.mouseMotionCallback.getFunctionPointer());
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
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = CEntryPointSetup.EnterCreateIsolatePrologue.class,
                        epilogue = CEntryPointSetup.LeaveTearDownIsolateEpilogue.class)
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

        new Sphere(new Point3d(-2, 2, -2), 1.5f, Color.magenta).draw();

        new Line(new Point3d(0, 0, 0), new Point3d(10, 0, 0), Color.red, 2f).draw();
        new Line(new Point3d(0, 0, 0), new Point3d(0, 10, 0), Color.green, 2f).draw();
        new Line(new Point3d(0, 0, 0), new Point3d(0, 0, 10), Color.blue, 2f).draw();

        new Arrow(new Point3d(-2, -2, -2), new Vector3d(1, 1, 1), 0.1, Color.cyan).draw();

        new Circle(new Point3d(-2, 3, 2), new Vector3d(1, 0, 0), 20, 2.0, Color.yellow, false, true).draw();
        new Circle(new Point3d(-2, 4, 3), new Vector3d(0, 1, 0), 20, 2.0, Color.red, true, false).draw();
        new Circle(new Point3d(-2, 5, 4), new Vector3d(0, -1, -1), 20, 2.0, Color.orange, false, false).draw();

        new Polygon(new Point3d[] {
            new Point3d(2, 2, 2),
            new Point3d(3, 3, 3),
            new Point3d(3, 0, 3),
            new Point3d(1, -1, 1)
        }, Color.blue).draw();

        new Cube(new Point3d(1, 0, 0), 1, new Color(0, 255, 127)).draw();
        new Cube(new Point3d(0, 0, 0), 1, new Color(255, 127, 0)).draw();
        new Cube(new Point3d(0, 0, 1), 1, new Color(127, 0, 255)).draw();

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
