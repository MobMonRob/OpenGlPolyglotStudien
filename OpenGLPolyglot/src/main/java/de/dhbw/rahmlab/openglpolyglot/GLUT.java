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
    static native int SINGLE();

    @CConstant("GLUT_RGB")
    static native int RGB();

    @CConstant("GLUT_DEPTH")
    static native int DEPTH();

    @CFunction("glutInit")
    static native void init(CIntPointer argc, CCharPointerPointer argv);

    @CFunction("glutInitDisplayMode")
    static native void initDisplayMode(int displayMode);

    @CFunction("glutInitWindowSize")
    static native void initWindowSize(int width, int height);

    @CFunction("glutInitWindowPosition")
    static native void initWindowPosition(int x, int y);

    @CFunction("glutDisplayFunc")
    static native void displayFunc(Callback callback);

    @CFunction("glutIdleFunc")
    static native void idleFunc(Callback callback);

    @CFunction("glutCreateWindow")
    static native void createWindow(CCharPointer name);

    @CFunction("glutMainLoop")
    static native void mainLoop();

    @CFunction("glutSolidSphere")
    static native void solidSphere(double radius, int slices, int stacks);

    @CFunction("glutWireSphere")
    static native void wireSphere(double radius, int slices, int stacks);

    @CFunction("glutSolidCube")
    static native void solidCube(double size);

    @CFunction("glutWireCube")
    static native void wireCube(double size);

    @CFunction("glutSolidCone")
    static native void solidCone(double base, double height, int slices, int stacks);

    @CFunction("glutWireCone")
    static native void wireCone(double base, double height, int slices, int stacks);
    
    @CFunction("glutSolidTorus")
    static native void solidTorus(double innerRadius, double outerRadius, int nsides, int rings);

    @CFunction("glutWireTorus")
    static native void wireTorus(double innerRadius, double outerRadius, int nsides, int rings);

    @CFunction("glutSolidDodecahedron")
    static native void solidDodecahedron();

    @CFunction("glutWireDodecahedron")
    static native void wireDodecahedron();

    @CFunction("glutSolidOctahedron")
    static native void solidOctahedron();

    @CFunction("glutWireOctahedron")
    static native void wireOctahedron();

    @CFunction("glutSolidTetrahedron")
    static native void solidTetrahedron();

    @CFunction("glutWireTetrahedron")
    static native void wireTetrahedron();

    @CFunction("glutSolidIcosahedron")
    static native void solidIcosahedron();

    @CFunction("glutWireIcosahedron")
    static native void wireIcosahedron();

    @CFunction("glutSolidTeapot")
    static native void solidTeapot(double size);

    @CFunction("glutWireTeapot")
    static native void wireTeapot(double size);

    @CFunction("glutPostRedisplay")
    static native void postRedisplay();
    
    @CFunction("glutSwapBuffers")
    static native void swapBuffers();

    @CFunction("glutReshapeFunc")
    static native void reshapeFunc(Callback callback);

    interface Callback extends CFunctionPointer {
        @InvokeCFunctionPointer void invoke();
    }
}