package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.clibraries.AI;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import org.jogamp.vecmath.Matrix4d;

public class Mesh implements Shape {

    private final AI.Scene scene;
    private final Matrix4d transform;

    public Mesh(AI.Scene scene, Matrix4d transform) {
        this.scene = scene;
        this.transform = transform;
        
    }

    @Override
    public void draw() {
        drawRecursive(scene.getRootNode());
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void drawRecursive(AI.Node nd) {

        //var m = nd.getTransformation();

	/* update transform */
	//AI.transposeMatrix4(m);
	GL.pushMatrix();
	//GL.multMatrixf(m);

        for (var n = 0; n < nd.getNumMeshes(); ++n) {
            var mesh = scene.getMeshes().addressOf(nd.getMeshes().addressOf(n).read());

            //apply_material(sc->mMaterials[mesh->mMaterialIndex]);

            if(mesh.getNormals().isNull()) {
                //GL.disable(GL.LIGHTING());
            } else {
                //GL.enable(GL.LIGHTING());
            }

            for (var t = 0; t < mesh.getNumFaces(); ++t) {
                var face = mesh.getFaces().addressOf(t);
                var face_mode = switch (face.getNumIndices()) {
                    case 1 -> GL.POINTS();
                    case 2 -> GL.LINES();
                    case 3 -> GL.TRIANGLES();
                    case 4 -> GL.POLYGON();
                    default -> GL.TRIANGLES();
                };

                GL.begin(face_mode);

                for (var i = 0; i < face.getNumIndices(); i++) {
                    var index = face.getIndices().addressOf(i).read();
                    if(mesh.getColors().addressOf(0).isNonNull())
                            GL.color4fv(mesh.getColors().addressOf(0).addressOf(index).r());
                    if(mesh.getNormals().isNonNull())
                            GL.normal3fv(mesh.getNormals().addressOf(index).x());
                    GL.vertex3fv(mesh.getVertices().addressOf(index).x());
                }

                GL.end();
            }
	}

        /* draw all children */
	for (int n = 0; n < nd.getNumChildren(); ++n) {
            drawRecursive(nd.getChildren().addressOf(n));
	}

	GL.popMatrix();
    }
}
