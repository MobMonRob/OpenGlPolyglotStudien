package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import de.dhbw.rahmlab.openglpolyglot.clibraries.Directives;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GLUT;
import com.oracle.svm.core.c.CGlobalData;
import com.oracle.svm.core.c.CGlobalDataFactory;
import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.orat.view3d.euclid3dviewapi.api.ViewerService;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.word.WordFactory;

@CContext(Directives.class)
public class OpenGLRenderer {

    public static final int INITIAL_WIDTH = 800;
    public static final int INITIAL_HEIGHT = 800;
    public static final int MAX_WIDTH = 3840;
    public static final int MAX_HEIGHT = 2160;
    private static int latestWidth = INITIAL_WIDTH;
    private static int latestHeight = INITIAL_HEIGHT;

    public static final CGlobalData<CFloatPointer> xRotation
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CFloatPointer> zRotation
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CFloatPointer> scale
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CIntPointer> width
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CIntPointer> height
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<@CUnsigned CIntPointer> fbo
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<@CUnsigned CIntPointer> texture
            = CGlobalDataFactory.createBytes(() -> 4);

    public static final CGlobalData<CCharPointer> pixelMap
            = CGlobalDataFactory.createBytes(() -> MAX_WIDTH * MAX_HEIGHT * 4);

    public static final CGlobalData<@CUnsigned CIntPointer> depthRenderbuffer
            = CGlobalDataFactory.createBytes(() -> 4);

    public static EuclidViewer3D viewer;

    public static void initialize() {
        viewer = (EuclidViewer3D) ViewerService.getInstance().getViewer().get();
        width.get().write(INITIAL_WIDTH);
        height.get().write(INITIAL_HEIGHT);
        xRotation.get().write(25f);
        zRotation.get().write(225f);
        scale.get().write(1f);

        initializeWindow();
        GLUT.hideWindow();
        setupFBO();

        while (true) {
            idle();
            display();
        }
    }

    private static void initializeWindow() {
        var arguments = StackValue.get(CCharPointerPointer.class);
        var argumentsSize = StackValue.get(CIntPointer.class);
        argumentsSize.write(0);

        GLUT.init(argumentsSize, arguments);
        GLUT.initDisplayMode(GLUT.DOUBLE() | GLUT.RGBA() | GLUT.DEPTH());
        GLUT.initWindowSize(1, 1);
        GLUT.initWindowPosition(0, 0);

        try (var title = CTypeConversion.toCString("GraalVM OpenGL")) {
            GLUT.createWindow(title.get());
        }
    }

    private static void setUpLightingAndMaterials() {
        GL.clearColor(1f, 1f, 1f, 1f);
        GL.shadeModel(GL.SMOOTH());

        GL.lightModeli(GL.LIGHT_MODEL_TWO_SIDE(), GL.TRUE());
        GL.colorMaterial(GL.FRONT_AND_BACK(), GL.DIFFUSE());
        GL.blendFunc(GL.SRC_ALPHA(), GL.ONE_MINUS_SRC_ALPHA());

        GL.enable(GL.LIGHT0());
        GL.enable(GL.DEPTH_TEST());
        GL.enable(GL.NORMALIZE());
        GL.enable(GL.BLEND());
    }

    private static void setupFBO() {
        GL.genFramebuffers(1, fbo.get());
        GL.bindFramebuffer(GL.FRAMEBUFFER(), fbo.get().read());

        setUpLightingAndMaterials();

        GL.genTextures(1, texture.get());
        GL.bindTexture(GL.TEXTURE_2D(), texture.get().read());
        GL.texImage2D(GL.TEXTURE_2D(), 0, GL.RGBA(), MAX_WIDTH, MAX_HEIGHT, 0, GL.RGBA(), GL.UNSIGNED_BYTE(), WordFactory.nullPointer());
        GL.texParameteri(GL.TEXTURE_2D(), GL.TEXTURE_MIN_FILTER(), GL.LINEAR());
        GL.texParameteri(GL.TEXTURE_2D(), GL.TEXTURE_MAG_FILTER(), GL.LINEAR());

        GL.framebufferTexture2D(GL.FRAMEBUFFER(), GL.COLOR_ATTACHMENT0(), GL.TEXTURE_2D(), texture.get().read(), 0);

        GL.genRenderbuffers(1, depthRenderbuffer.get());
        GL.bindRenderbuffer(GL.RENDERBUFFER(), depthRenderbuffer.get().read());
        GL.renderbufferStorage(GL.RENDERBUFFER(), GL.DEPTH_COMPONENT24(), MAX_WIDTH, MAX_HEIGHT);
        GL.framebufferRenderbuffer(GL.FRAMEBUFFER(), GL.DEPTH_ATTACHMENT(), GL.RENDERBUFFER(), depthRenderbuffer.get().read());

        if (GL.checkFramebufferStatus(GL.FRAMEBUFFER()) != GL.FRAMEBUFFER_COMPLETE()) {
            System.out.println("FBO not complete!");
        }

        GL.bindFramebuffer(GL.FRAMEBUFFER(), 0);
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = IsolateSingleton.Prologue.class,
                        epilogue = CEntryPointSetup.LeaveEpilogue.class)
    private static void display() {
        var scalef = scale.get().read();
        GL.bindFramebuffer(GL.FRAMEBUFFER(), fbo.get().read());
        reshape(width.get().read(), height.get().read());
        GL.clear(GL.COLOR_BUFFER_BIT() | GL.DEPTH_BUFFER_BIT());
        GL.loadIdentity();
        
        // rotate the coordinate system so the z-axis is up, instead of y:
        GL.rotatef(-90.0f, 1.0f, 0.0f, 0.0f);

        // apply zoom & roation from user input:
        GL.scalef(scalef, scalef, scalef);
        GL.rotatef(xRotation.get().read(), 1f, 0f, 0f);
        GL.rotatef(zRotation.get().read(), 0f, 0f, 1f);

        Shape.drawAll(viewer.getNodes().values());
        ((AABB) viewer.getAABB()).draw();

        updatePixelMap(width.get().read(), height.get().read());
        GL.bindFramebuffer(GL.FRAMEBUFFER(), 0);
    }

    private static void idle() {
        var currentWidth = width.get().read();
        var currentHeight = height.get().read();

        if (currentWidth != latestWidth || currentHeight != latestHeight) {
            latestWidth = currentWidth;
            latestHeight = currentHeight;
        }
    }

    private static void reshape(int width, int height) {
        double ratio = (double) width / (double) height;
        GL.viewport(0, 0, width, height);
        GL.matrixMode(GL.PROJECTION());
        GL.loadIdentity();
        GL.ortho(-10 * ratio, 10 * ratio, -10, 10, -100, 100);
        GL.matrixMode(GL.MODELVIEW());
    }

    private static void updatePixelMap(int width, int height) {
        GL.readPixels(0, 0, width, height, GL.RGBA(), GL.UNSIGNED_BYTE(), pixelMap.get().addressOf(0));
    }
}
