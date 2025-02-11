package de.dhbw.rahmlab.openglpolyglot.clibraries;

import java.util.List;
import org.graalvm.nativeimage.c.CContext;

public final class Directives implements CContext.Directives {
    @Override
    public List<String> getHeaderFiles() {
        return List.of(
                "<GL/glu.h>",
                "<GL/glut.h>",
                "<assimp/cimport.h>",
                "<assimp/scene.h>",
                "<assimp/postprocess.h>",
                "<FTGL/ftgl.h>");
    }

    @Override
    public List<String> getLibraries() {
        return List.of(
                "GL",
                "GLU",
                "glut",
                "assimp",
                "ftgl");
    }

    @Override
    public List<String> getOptions() {

        if (System.getProperty("os.name").startsWith("Windows")) {
            return List.of(); // TODO: add compiler options for windows
        }

        return List.of("-I/usr/include/freetype2");
    }
}