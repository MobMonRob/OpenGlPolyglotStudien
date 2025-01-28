package de.dhbw.rahmlab.openglpolyglot;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.word.PointerBase;

@CContext(Directives.class)
public class GL {
    @CConstant("GL_FLAT")
    public static native int FLAT();

    @CConstant("GL_SMOOTH")
    public static native int SMOOTH();

    @CConstant("GL_LIGHT0")
    public static native int LIGHT0();

    @CConstant("GL_AMBIENT")
    public static native int AMBIENT();

    @CConstant("GL_DIFFUSE")
    public static native int DIFFUSE();

    @CConstant("GL_FRONT")
    public static native int FRONT();

    @CConstant("GL_SHININESS")
    public static native int SHININESS();

    @CConstant("GL_LIGHTING")
    public static native int LIGHTING();

    @CConstant("GL_DEPTH_TEST")
    public static native int DEPTH_TEST();

    @CConstant("GL_BLEND")
    public static native int BLEND();

    @CConstant("GL_SRC_ALPHA")
    public static native int SRC_ALPHA();

    @CConstant("GL_ONE_MINUS_SRC_ALPHA")
    public static native int ONE_MINUS_SRC_ALPHA();

    @CConstant("GL_COLOR_BUFFER_BIT")
    public static native int COLOR_BUFFER_BIT();

    @CConstant("GL_DEPTH_BUFFER_BIT")
    public static native int DEPTH_BUFFER_BIT();

    @CConstant("GL_LINES")
    public static native int LINES();

    @CConstant("GL_QUADS")
    public static native int QUADS();
    
    @CConstant("GL_PROJECTION")
    public static native int PROJECTION();

    @CConstant("GL_MODELVIEW")
    public static native int MODELVIEW();

    @CConstant("GL_TRIANGLE_FAN")
    public static native int TRIANGLE_FAN();

    @CConstant("GL_LINE_STRIP")
    public static native int LINE_STRIP();

    @CConstant("GL_POLYGON")
    public static native int POLYGON();

    @CConstant("GL_INT")
    public static native int INT();

    @CConstant("GL_UNSIGNED_BYTE")
    public static native int UNSIGNED_BYTE();

    @CConstant("GL_RGB")
    public static native int RGB();

    @CConstant("GL_RGBA")
    public static native int RGBA();

    @CFunction("glClearColor")
    public static native void clearColor(float red, float green, float blue, float alpha);

    @CFunction("glColor3i")
    public static native void color3i(int red, int green, int blue);

    @CFunction("glColor4i")
    public static native void color4i(int red, int green, int blue, int alpha);

    @CFunction("glColor3f")
    public static native void color3f(float red, float green, float blue);

    @CFunction("glColor3ub")
    public static native void color3ub(@CUnsigned int red, @CUnsigned int green, @CUnsigned int blue);

    @CFunction("glColor4f")
    public static native void color4f(float red, float green, float blue, float alpha);

    @CFunction("glColor4ub")
    public static native void color4ub(@CUnsigned int red, @CUnsigned int green, @CUnsigned int blue, @CUnsigned int alpha);

    @CFunction("glShadeModel")
    public static native void shadeModel(int mode);

    @CFunction("glLightfv")
    public static native void lightfv(int light, int pname, final CFloatPointer params);

    @CFunction("glMaterialfv")
    public static native void materialfv(int face, int pname, final CFloatPointer params);

    @CFunction("glEnable")
    public static native void enable(int cap);

    @CFunction("glClear")
    public static native void clear(int mask);

    @CFunction("glPushMatrix")
    public static native void pushMatrix();

    @CFunction("glPopMatrix")
    public static native void popMatrix();

    @CFunction("glRotatef")
    public static native void rotatef(float angle, float x, float y, float z);

    @CFunction("glRotated")
    public static native void rotated(double angle, double x, double y, double z);

    @CFunction("glFlush")
    public static native void flush();

    @CFunction("glBegin")
    public static native void begin(int mode);

    @CFunction("glVertex3f")
    public static native void vertex3f(float x, float y, float z);

    @CFunction("glVertex2d")
    public static native void vertex2d(double x, double y);

    @CFunction("glVertex3d")
    public static native void vertex3d(double x, double y, double z);

    @CFunction("glLoadIdentity")
    public static native void loadIdentity();

    @CFunction("glViewport")
    public static native void viewport(int x, int y, int width, int height);

    @CFunction("glMatrixMode")
    public static native void matrixMode(int mode);

    @CFunction("glEnd")
    public static native void end();

    @CFunction("glTranslatef")
    public static native void translatef(float x, float y, float z);

    @CFunction("glTranslated")
    public static native void translated(double x, double y, double z);

    @CFunction("glScalef")
    public static native void scalef(float x, float y, float z);

    @CFunction("glOrtho")
    public static native void ortho(double left, double right, double bottom, double top, double near, double far);

    @CFunction("glLineWidth")
    public static native void lineWidth(float width);

    @CFunction("glRasterPos3d")
    public static native void rasterPos3d(double x, double y, double z);
    
    @CFunction("glBlendFunc")
    public static native void blendFunc(int sfactor, int dfactor);

    @CFunction("glReadPixels")
    public static native void readPixels(int x, int y, int width, int height, int format, int type, PointerBase data);
}
