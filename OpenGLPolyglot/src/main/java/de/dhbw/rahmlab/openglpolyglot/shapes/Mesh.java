package de.dhbw.rahmlab.openglpolyglot.shapes;

import de.dhbw.rahmlab.openglpolyglot.AABB;
import de.dhbw.rahmlab.openglpolyglot.clibraries.AI;
import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import org.graalvm.nativeimage.StackValue;
import org.graalvm.nativeimage.c.type.CFloatPointer;
import org.graalvm.nativeimage.c.type.CIntPointer;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public final class Mesh implements Shape {

    private final AI.Scene scene;
    private final Point3d location;
    private final Vector3d direction;

    private double aabbMinX, aabbMinY, aabbMinZ, aabbMaxX, aabbMaxY, aabbMaxZ;

    public Mesh(AI.Scene scene, Matrix4d transformMatrix) {
        this.scene = scene;
        this.location = new Point3d(0, 0, 0);
        this.direction = new Vector3d(0, 0, 1);
        this.aabbMinX = Double.MAX_VALUE;
        this.aabbMinY = Double.MAX_VALUE;
        this.aabbMinZ = Double.MAX_VALUE;
        this.aabbMaxX = Double.MIN_VALUE;
        this.aabbMaxY = Double.MIN_VALUE;
        this.aabbMaxZ = Double.MIN_VALUE;

        transform(transformMatrix);
        initlializeAABBValues(scene.getRootNode(), transformMatrix);
        AI.transposeMatrix4(scene.getRootNode().getTransformation());
    }

    @Override
    public void draw() {
        GL.translated(location.x, location.y, location.z);
        var rotationAngle = ShapeDrawingUtils.getAngleToZAxis(direction);
        GL.rotated(rotationAngle, -direction.y, direction.x, 0);

        drawNodeRecursive(scene.getRootNode());

        GL.disable(GL.LIGHTING());
        GL.disable(GL.CULL_FACE());
        GL.rotated(-rotationAngle, -direction.y, direction.x, 0);
        GL.translated(-location.x, -location.y, -location.z);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void transform(Matrix4d transformMatrix) {
        transformMatrix.transform(location);
        transformMatrix.transform(direction);
    }

    @Override
    public AABB getAABB() {
        return new AABB(aabbMinX, aabbMinY, aabbMinZ, aabbMaxX, aabbMaxY, aabbMaxZ);
    }

    private void drawNodeRecursive(AI.Node node) {

        GL.pushMatrix();
        GL.multMatrixf((CFloatPointer) node.getTransformation());

        // draw all meshes in node
        for (var n = 0; n < node.getNumMeshes(); n++) {
            var mesh = scene.getMeshes().addressOf(node.getMeshes().addressOf(n).read()).read();
            drawMesh(mesh);
        }

        // draw all child nodes
        for (var n = 0; n < node.getNumChildren(); n++) {
            drawNodeRecursive(node.getChildren().addressOf(n).read());
        }

        GL.popMatrix();
    }

    private void drawMesh(AI.Mesh mesh) {

        applyMaterial(scene.getMaterials().addressOf(mesh.getMaterialIndex()).read());

        if (mesh.getNormals().isNull()) {
            GL.disable(GL.LIGHTING());
        } else {
            GL.enable(GL.LIGHTING());
        }

        // draw all faces in mesh
        for (var i = 0; i < mesh.getNumFaces(); i++) {
            drawFace(mesh.getFaces().addressOf(i), mesh);
        }
    }

    private void drawFace(AI.Face face, AI.Mesh mesh) {

        var faceMode = switch (face.getNumIndices()) {
            case 1 -> GL.POINTS();
            case 2 -> GL.LINES();
            case 3 -> GL.TRIANGLES();
            default -> GL.POLYGON();
        };

        GL.begin(faceMode);

        for (var i = 0; i < face.getNumIndices(); i++) {
            var index = face.getIndices().addressOf(i).read();
            if (mesh.getNormals().isNonNull()) {
                GL.normal3fv(mesh.getNormals().addressOf(index).x());
            }
            GL.vertex3fv(mesh.getVertices().addressOf(index).x());
        }

        GL.end();
    }

    private void applyMaterial(AI.Material mtl) {

        var colorComponents = StackValue.get(4, CFloatPointer.class);

        var diffuse  = StackValue.get(1, AI.Color4D.class);
        var specular = StackValue.get(1, AI.Color4D.class);
        var ambient  = StackValue.get(1, AI.Color4D.class);
        var emission = StackValue.get(1, AI.Color4D.class);

        var shininess = StackValue.get(1, CFloatPointer.class);
        var strength  = StackValue.get(1, CFloatPointer.class);

        var twoSided  = StackValue.get(1, CIntPointer.class);
        var wireframe = StackValue.get(1, CIntPointer.class);

        setFloatArrayValues(colorComponents, 0.8f, 0.8f, 0.8f, 1.0f);
        if (AI.getMaterialColor(mtl, CTypeConversion.toCString("$clr.diffuse").get(), 0, 0, diffuse) == AI.SUCCESS()) {
            fillFloatArrayWithColor(diffuse, colorComponents);
        }
        GL.materialfv(GL.FRONT_AND_BACK(), GL.DIFFUSE(), colorComponents);

        setFloatArrayValues(colorComponents, 0.0f, 0.0f, 0.0f, 1.0f);
        if (AI.getMaterialColor(mtl, CTypeConversion.toCString("$clr.specular").get(), 0, 0, specular) == AI.SUCCESS()) {
            fillFloatArrayWithColor(specular, colorComponents);
        }
        GL.materialfv(GL.FRONT_AND_BACK(), GL.SPECULAR(), colorComponents);

        setFloatArrayValues(colorComponents, 0.2f, 0.2f, 0.2f, 1.0f);
        if (AI.getMaterialColor(mtl, CTypeConversion.toCString("$clr.ambient").get(), 0, 0, ambient) == AI.SUCCESS()) {
            fillFloatArrayWithColor(ambient, colorComponents);
        }
        GL.materialfv(GL.FRONT_AND_BACK(), GL.AMBIENT(), colorComponents);

        setFloatArrayValues(colorComponents, 0.0f, 0.0f, 0.0f, 1.0f);
        if (AI.getMaterialColor(mtl, CTypeConversion.toCString("$clr.emissive").get(), 0, 0, emission) == AI.SUCCESS()) {
            fillFloatArrayWithColor(emission, colorComponents);
        }
        GL.materialfv(GL.FRONT_AND_BACK(), GL.EMISSION(), colorComponents);

        var max = StackValue.get(1, CIntPointer.class);
        max.write(1);
        var ret1 = AI.getMaterialFloatArray(mtl, CTypeConversion.toCString("$mat.shininess").get(), 0, 0, shininess, max);
        if (ret1 == AI.SUCCESS()) {
            max.write(1);
            var ret2 = AI.getMaterialFloatArray(mtl, CTypeConversion.toCString("$mat.shinpercent").get(), 0, 0, strength, max);
            if (ret2 == AI.SUCCESS()) {
                GL.materialf(GL.FRONT_AND_BACK(), GL.SHININESS(), shininess.read() * strength.read());
            } else {
                GL.materialf(GL.FRONT_AND_BACK(), GL.SHININESS(), shininess.read());
            }
        } else {
            GL.materialf(GL.FRONT_AND_BACK(), GL.SHININESS(), 0.0f);
            setFloatArrayValues(colorComponents, 0.0f, 0.0f, 0.0f, 0.0f);
            GL.materialfv(GL.FRONT_AND_BACK(), GL.SPECULAR(), colorComponents);
        }

        max.write(1);
        int fillMode;
        if (AI.getMaterialIntegerArray(mtl, CTypeConversion.toCString("$mat.wireframe").get(), 0, 0, wireframe, max) == AI.SUCCESS()) {
            fillMode = wireframe.read() > 0 ? GL.LINE() : GL.FILL();
        } else {
            fillMode = GL.FILL();
        }
        GL.polygonMode(GL.FRONT_AND_BACK(), fillMode);

        max.write(1);
        if ((AI.getMaterialIntegerArray(mtl, CTypeConversion.toCString("$mat.twosided").get(), 0, 0, twoSided, max) == AI.SUCCESS()) && twoSided.read() == 1) {
            GL.disable(GL.CULL_FACE());
        } else {
            GL.enable(GL.CULL_FACE());
        }
    }

    private void setFloatArrayValues(CFloatPointer array, float a, float b, float c, float d) {
        array.write(0, a);
        array.write(1, b);
        array.write(2, c);
        array.write(3, d);
    }

    private void fillFloatArrayWithColor(AI.Color4D color, CFloatPointer array) {
        setFloatArrayValues(array, color.getR(), color.getG(), color.getB(), color.getA());
    }

    private void initlializeAABBValues(AI.Node node, Matrix4d transformMatrix) {
        for (var meshIndex = 0; meshIndex < node.getNumMeshes(); meshIndex++) {
            var mesh = scene.getMeshes().addressOf(node.getMeshes().addressOf(meshIndex).read()).read();
            includeIntoAABB(mesh, transformMatrix);
        }

        for (var childNodeIndex = 0; childNodeIndex < node.getNumChildren(); childNodeIndex++) {
            initlializeAABBValues(node.getChildren().addressOf(childNodeIndex).read(), transformMatrix);
        }
    }

    private void includeIntoAABB(AI.Mesh mesh, Matrix4d transformMatrix) {
        var meshMinX = mesh.getAABB().getMin().x().read() / 1000;
        var meshMinY = mesh.getAABB().getMin().y().read() / 1000;
        var meshMinZ = mesh.getAABB().getMin().z().read() / 1000;
        var meshMaxX = mesh.getAABB().getMax().x().read() / 1000;
        var meshMaxY = mesh.getAABB().getMax().y().read() / 1000;
        var meshMaxZ = mesh.getAABB().getMax().z().read() / 1000;

        var minPoint = new Point3d(meshMinX, meshMinY, meshMinZ);
        var maxPoint = new Point3d(meshMaxX, meshMaxY, meshMaxZ);

        transformMatrix.transform(minPoint);
        transformMatrix.transform(maxPoint);

        if (minPoint.x < this.aabbMinX) this.aabbMinX = minPoint.x;
        if (minPoint.y < this.aabbMinY) this.aabbMinY = minPoint.y;
        if (minPoint.z < this.aabbMinZ) this.aabbMinZ = minPoint.z;
        if (maxPoint.x > this.aabbMaxX) this.aabbMaxX = maxPoint.x;
        if (maxPoint.y > this.aabbMaxY) this.aabbMaxY = maxPoint.y;
        if (maxPoint.z > this.aabbMaxZ) this.aabbMaxZ = maxPoint.z;
    }
}
