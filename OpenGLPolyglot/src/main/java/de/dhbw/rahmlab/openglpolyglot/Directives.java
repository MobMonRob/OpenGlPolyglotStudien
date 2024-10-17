package de.dhbw.rahmlab.openglpolyglot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.c.CContext;

public final class Directives implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return Collections.singletonList("<GL/glut.h>");
    }

    @Override
    public List<String> getLibraries() {
        return Arrays.asList("GL", "glut");
    }
}
