package de.dhbw.rahmlab.openglpolyglot;

public class ShapeDrawer {

    public static void drawCube() {
        GL.begin(GL.QUADS());
        GL.color3f(1, 0, 0);

        // vorne
        GL.vertex3f(-1f, 1f, 1f);
        GL.vertex3f(-1f, -1f, 1f);
        GL.vertex3f(1f, -1f, 1f);
        GL.vertex3f(1f, 1f, 1f);

        // hinten
        GL.vertex3f(-1f, 1f, -1f);
        GL.vertex3f(-1f, -1f, -1f);
        GL.vertex3f(1f, -1f, -1f);
        GL.vertex3f(1f, 1f, -1f);

        GL.color3f(0, 1, 0);

        // oben
        GL.vertex3f(-1, 1, -1);
        GL.vertex3f(-1, 1, 1);
        GL.vertex3f(1, 1, 1);
        GL.vertex3f(1, 1, -1);

        // unten
        GL.vertex3f(-1, -1, -1);
        GL.vertex3f(-1, -1, 1);
        GL.vertex3f(1, -1, 1);
        GL.vertex3f(1, -1, -1);

        GL.color3f(0, 0, 1);

        // rechts
        GL.vertex3f(1, 1, 1);
        GL.vertex3f(1, -1, 1);
        GL.vertex3f(1, -1, -1);
        GL.vertex3f(1, 1, -1);

        // links
        GL.vertex3f(-1, 1, 1);
        GL.vertex3f(-1, -1, 1);
        GL.vertex3f(-1, -1, -1);
        GL.vertex3f(-1, 1, -1);
    }
}
