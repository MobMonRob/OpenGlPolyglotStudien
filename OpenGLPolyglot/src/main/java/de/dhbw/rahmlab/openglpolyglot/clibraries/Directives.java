package de.dhbw.rahmlab.openglpolyglot.clibraries;

import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.c.CContext;

public final class Directives implements CContext.Directives {

    @Override
    public List<String> getHeaderFiles() {

        if (System.getProperty("os.name").startsWith("Windows")) {
            return List.of(
                "<windows.h>",
                "<GL/glu.h>",
                "<GL/glut.h>",
                "<assimp/cimport.h>",
                "<assimp/scene.h>",
                "<assimp/postprocess.h>",
                "<FTGL/ftgl.h>");
        }

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

        if (System.getProperty("os.name").startsWith("Windows")) {
            return List.of(
                "opengl32",
                "glu32",
                "freeglut",
                "assimp-vc143-mt",
                "ftgl_D");
        }

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
            return Collections.EMPTY_LIST;
        }

        return List.of("-I/usr/include/freetype2");
    }
}