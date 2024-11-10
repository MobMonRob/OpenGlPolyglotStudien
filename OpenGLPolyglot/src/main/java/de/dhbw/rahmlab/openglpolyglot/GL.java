package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;

@CContext(Directives.class)
public class GL {
    @CConstant("GL_FLAT")
    static native int FLAT();

    @CConstant("GL_SMOOTH")
    static native int SMOOTH();

    @CConstant("GL_LIGHT0")
    static native int LIGHT0();

    @CConstant("GL_AMBIENT")
    static native int AMBIENT();

    @CConstant("GL_DIFFUSE")
    static native int DIFFUSE();

    @CConstant("GL_FRONT")
    static native int FRONT();

    @CConstant("GL_SHININESS")
    static native int SHININESS();

    @CConstant("GL_LIGHTING")
    static native int LIGHTING();

    @CConstant("GL_DEPTH_TEST")
    static native int DEPTH_TEST();

    @CConstant("GL_COLOR_BUFFER_BIT")
    static native int COLOR_BUFFER_BIT();

    @CConstant("GL_DEPTH_BUFFER_BIT")
    static native int DEPTH_BUFFER_BIT();

    @CConstant("GL_LINES")
    static native int LINES();

    @CConstant("GL_QUADS")
    static native int QUADS();
    
    @CConstant("GL_PROJECTION")
    static native int PROJECTION();

    @CConstant("GL_MODELVIEW")
    static native int MODELVIEW();

    @CFunction("glClearColor")
    static native void clearColor(float red, float green, float blue, float alpha);

    @CFunction("glColor3i")
    static native void color3i(int red, int green, int blue);

    @CFunction("glColor4i")
    static native void color4i(int red, int green, int blue, int alpha);

    @CFunction("glColor3f")
    static native void color3f(float red, float green, float blue);

    @CFunction("glColor3ub")
    static native void color3ub(@CUnsigned int red, @CUnsigned int green, @CUnsigned int blue);

    @CFunction("glShadeModel")
    static native void shadeModel(int mode);

    @CFunction("glLightfv")
    static native void lightfv(int light, int pname, final CFloatPointer params);

    @CFunction("glMaterialfv")
    static native void materialfv(int face, int pname, final CFloatPointer params);

    @CFunction("glEnable")
    static native void enable(int cap);

    @CFunction("glClear")
    static native void clear(int mask);

    @CFunction("glPushMatrix")
    static native void pushMatrix();

    @CFunction("glPopMatrix")
    static native void popMatrix();

    @CFunction("glRotatef")
    static native void rotatef(float angle, float x, float y, float z);

    @CFunction("glFlush")
    static native void flush();

    @CFunction("glBegin")
    static native void begin(int mode);

    @CFunction("glVertex3f")
    static native void vertex3f(float x, float y, float z);

    @CFunction("glVertex3d")
    static native void vertex3d(double x, double y, double z);

    @CFunction("glLoadIdentity")
    static native void loadIdentity();

    @CFunction("glViewport")
    static native void viewport(int x, int y, int width, int height);

    @CFunction("glMatrixMode")
    static native void matrixMode(int mode);

    @CFunction("glEnd")
    static native void end();

    @CFunction("glTranslatef")
    static native void translatef(float x, float y, float z);

    @CFunction("glTranslated")
    static native void translated(double x, double y, double z);

    @CFunction("glScalef")
    static native void scalef(float x, float y, float z);

    @CFunction("glOrtho")
    static native void ortho(double left, double right, double bottom, double top, double near, double far);

    @CFunction("glLineWidth")
    static native void lineWidth(float width);
}
