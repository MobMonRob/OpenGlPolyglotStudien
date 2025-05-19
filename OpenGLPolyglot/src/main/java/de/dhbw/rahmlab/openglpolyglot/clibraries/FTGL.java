package de.dhbw.rahmlab.openglpolyglot.clibraries;

import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.constant.CConstant;
import org.graalvm.nativeimage.c.function.CFunction;
import org.graalvm.nativeimage.c.type.CCharPointer;
import org.graalvm.nativeimage.c.type.CUnsigned;
import org.graalvm.word.PointerBase;

/**
 * Constants, functions and structs of the FTGL library.
 * These bindings do not provide every feature of the library and may have to be
 * expanded when needed.
 */
@CContext(Directives.class)
public class FTGL {

    @CConstant("FTGL_RENDER_ALL")
    public static native int RENDER_ALL();

    @CFunction("ftglCreatePixmapFont")
    public static native Font createPixmapFont(CCharPointer fontPath);

    @CFunction("ftglSetFontFaceSize")
    public static native int setFontFaceSize(Font font, @CUnsigned int size, @CUnsigned int res);

    @CFunction("ftglRenderFont")
    public static native void renderFont(Font font, CCharPointer string, int mode);

    @CFunction("ftglDestroyFont")
    public static native void destroyFont(Font font);

    public interface Font extends PointerBase {};
}
