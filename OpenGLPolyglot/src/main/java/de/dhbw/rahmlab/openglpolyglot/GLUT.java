package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.function.CFunctionPointer;
import org.graalvm.nativeimage.c.function.InvokeCFunctionPointer;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CCharPointerPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;

@CContext(Directives.class)
public class GLUT {
    @CConstant("GLUT_SINGLE")
    public static native int SINGLE();

    @CConstant("GLUT_RGB")
    public static native int RGB();

    @CConstant("GLUT_DEPTH")
    public static native int DEPTH();

    @CFunction("glutInit")
    public static native void init(CIntPointer argc, CCharPointerPointer argv);

    @CFunction("glutInitDisplayMode")
    public static native void initDisplayMode(int displayMode);

    @CFunction("glutInitWindowSize")
    public static native void initWindowSize(int width, int height);

    @CFunction("glutInitWindowPosition")
    public static native void initWindowPosition(int x, int y);

    @CFunction("glutDisplayFunc")
    public static native void displayFunc(Callback callback);

    @CFunction("glutIdleFunc")
    public static native void idleFunc(Callback callback);

    @CFunction("glutCreateWindow")
    public static native void createWindow(CCharPointer name);

    @CFunction("glutMainLoop")
    public static native void mainLoop();

    @CFunction("glutSolidSphere")
    public static native void solidSphere(double radius, int slices, int stacks);

    @CFunction("glutWireSphere")
    public static native void wireSphere(double radius, int slices, int stacks);

    @CFunction("glutSolidCube")
    public static native void solidCube(double size);

    @CFunction("glutWireCube")
    public static native void wireCube(double size);

    @CFunction("glutSolidCone")
    public static native void solidCone(double base, double height, int slices, int stacks);

    @CFunction("glutWireCone")
    public static native void wireCone(double base, double height, int slices, int stacks);
    
    @CFunction("glutSolidTorus")
    public static native void solidTorus(double innerRadius, double outerRadius, int nsides, int rings);

    @CFunction("glutWireTorus")
    public static native void wireTorus(double innerRadius, double outerRadius, int nsides, int rings);

    @CFunction("glutSolidDodecahedron")
    public static native void solidDodecahedron();

    @CFunction("glutWireDodecahedron")
    public static native void wireDodecahedron();

    @CFunction("glutSolidOctahedron")
    public static native void solidOctahedron();

    @CFunction("glutWireOctahedron")
    public static native void wireOctahedron();

    @CFunction("glutSolidTetrahedron")
    public static native void solidTetrahedron();

    @CFunction("glutWireTetrahedron")
    public static native void wireTetrahedron();

    @CFunction("glutSolidIcosahedron")
    public static native void solidIcosahedron();

    @CFunction("glutWireIcosahedron")
    public static native void wireIcosahedron();

    @CFunction("glutSolidTeapot")
    public static native void solidTeapot(double size);

    @CFunction("glutWireTeapot")
    public static native void wireTeapot(double size);

    @CFunction("glutPostRedisplay")
    public static native void postRedisplay();
    
    @CFunction("glutSwapBuffers")
    public static native void swapBuffers();

    @CFunction("glutReshapeFunc")
    public static native void reshapeFunc(Callback2i callback);

    @CFunction("glutMouseFunc")
    public static native void mouseFunc(Callback4i callback);

    @CFunction("glutMotionFunc")
    public static native void motionFunc(Callback2i callback);

    @CFunction("glutPassiveMotionFunc")
    public static native void passiveMotionFunc(Callback2i callback);

    interface Callback extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke();
    }

    // Callback mit 2 int Parametern
    interface Callback2i extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke(int arg1, int arg2);
    }

    // Callback mit 4 int Parametern
    interface Callback4i extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke(int arg1, int arg2, int arg3, int arg4);
    }
}