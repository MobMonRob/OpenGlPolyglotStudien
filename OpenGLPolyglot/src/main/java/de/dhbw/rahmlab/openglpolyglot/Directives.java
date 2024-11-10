package de.dhbw.rahmlab.openglpolyglot;

import java.util.Arrays;
import java.util.List;
import org.graalvm.nativeimage.c.CContext;

public final class Directives implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return Arrays.asList("<GL/glu.h>", "<GL/glut.h>");
    }

    @Override
    public List<String> getLibraries() {
        return Arrays.asList("GL", "GLU", "glut");
    }
}
