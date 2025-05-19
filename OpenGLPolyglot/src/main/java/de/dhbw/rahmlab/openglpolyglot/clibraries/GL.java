package de.dhbw.rahmlab.openglpolyglot.clibraries;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.nativeimage.c.type.VoidPointer;
import org.graalvm.word.PointerBase;

/**
 * Constants, functions and structs of the OpenGL API.
 * These bindings do not provide every feature of the API and may have to be
 * expanded when needed.
 */
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

    @CConstant("GL_POINTS")
    public static native int POINTS();
    
    @CConstant("GL_LINES")
    public static native int LINES();

    @CConstant("GL_TRIANGLES")
    public static native int TRIANGLES();

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

    @CConstant("GL_FRONT_AND_BACK")
    public static native int FRONT_AND_BACK();

    @CConstant("GL_SPECULAR")
    public static native int SPECULAR();

    @CConstant("GL_EMISSION")
    public static native int EMISSION();

    @CConstant("GL_LINE")
    public static native int LINE();
    
    @CConstant("GL_FILL")
    public static native int FILL();

    @CConstant("GL_CULL_FACE")
    public static native int CULL_FACE();

    @CConstant("GL_LIGHT_MODEL_TWO_SIDE")
    public static native int LIGHT_MODEL_TWO_SIDE();

    @CConstant("GL_TRUE")
    public static native int TRUE();

    @CConstant("GL_NORMALIZE")
    public static native int NORMALIZE();

    @CConstant("GL_POSITION")
    public static native int POSITION();

    @CConstant("GL_LIGHT_MODEL_AMBIENT")
    public static native int LIGHT_MODEL_AMBIENT();

    @CConstant("GL_COLOR_MATERIAL")
    public static native int COLOR_MATERIAL();

    @CConstant("GL_AMBIENT_AND_DIFFUSE")
    public static native int AMBIENT_AND_DIFFUSE();

    @CConstant("GL_FRAMEBUFFER")
    public static native int FRAMEBUFFER();

    @CConstant("GL_FRAMEBUFFER_COMPLETE")
    public static native int FRAMEBUFFER_COMPLETE();

    @CConstant("GL_TEXTURE_2D")
    public static native int TEXTURE_2D();

    @CConstant("GL_TEXTURE_MIN_FILTER")
    public static native int TEXTURE_MIN_FILTER();

    @CConstant("GL_TEXTURE_MAG_FILTER")
    public static native int TEXTURE_MAG_FILTER();

    @CConstant("GL_LINEAR")
    public static native int LINEAR();

    @CConstant("GL_COLOR_ATTACHMENT0")
    public static native int COLOR_ATTACHMENT0();

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
    
    @CFunction("glColor4fv")
    public static native void color4fv(CFloatPointer v);

    @CFunction("glColor4ub")
    public static native void color4ub(@CUnsigned int red, @CUnsigned int green, @CUnsigned int blue, @CUnsigned int alpha);

    @CFunction("glNormal3fv")
    public static native void normal3fv(CFloatPointer v);

    @CFunction("glVertex3fv")
    public static native void vertex3fv(CFloatPointer v);

    @CFunction("glShadeModel")
    public static native void shadeModel(int mode);

    @CFunction("glLightfv")
    public static native void lightfv(int light, int pname, CFloatPointer params);

    @CFunction("glMaterialf")
    public static native void materialf(int face, int pname, float param);

    @CFunction("glMaterialfv")
    public static native void materialfv(int face, int pname, CFloatPointer params);

    @CFunction("glEnable")
    public static native void enable(int cap);

    @CFunction("glDisable")
    public static native void disable(int cap);

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

    @CFunction("glMultMatrixf")
    public static native void multMatrixf(CFloatPointer m);

    @CFunction("glPolygonMode")
    public static native void polygonMode(int face, int mode);

    @CFunction("glLightModeli")
    public static native void lightModeli(int pname, int param);

    @CFunction("glColorMaterial")
    public static native void colorMaterial(int face, int mode);

    @CFunction("glLightModelfv")
    public static native void lightModelfv(int pname, CFloatPointer params);

    @CFunction("glGenFramebuffers")
    public static native void genFramebuffers(int n, @CUnsigned CIntPointer ids);

    @CFunction("glBindFramebuffer")
    public static native void bindFramebuffer(int target, @CUnsigned int framebuffer);

    @CFunction("glGenTextures")
    public static native void genTextures(int n, @CUnsigned CIntPointer textures);

    @CFunction("glBindTexture")
    public static native void bindTexture(int target, @CUnsigned int textures);

    @CFunction("glTexImage2D")
    public static native void texImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, VoidPointer data);

    @CFunction("glTexParameteri")
    public static native void texParameteri(int target, int pname, int param);

    @CFunction("glFramebufferTexture2D")
    public static native void framebufferTexture2D(int target, int attachment, int textarget, @CUnsigned int texture, int level);

    @CFunction("glCheckFramebufferStatus")
    public static native int checkFramebufferStatus(int target);
}
