package de.dhbw.rahmlab.openglpolyglot.clibraries;

import java.util.Arrays;
import java.util.List;
import org.graalvm.nativeimage.c.CContext;

public final class Directives implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return Arrays.asList(
                "<GL/glu.h>",
                "<GL/glut.h>",
                "<assimp/cimport.h>",
                "<assimp/scene.h>",
                "<assimp/postprocess.h>");
    }

    @Override
    public List<String> getLibraries() {
        return Arrays.asList(
                "GL",
                "GLU",
                "glut",
                "OpenGLPolyglotHelper",
                "assimp");
    }
}