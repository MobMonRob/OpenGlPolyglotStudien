package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.clibraries.AI;
import de.dhbw.rahmlab.openglpolyglot.clibraries.Directives;
import de.dhbw.rahmlab.openglpolyglot.shapes.Arrow;
import de.dhbw.rahmlab.openglpolyglot.shapes.Circle;
import de.dhbw.rahmlab.openglpolyglot.shapes.Cube;
import de.dhbw.rahmlab.openglpolyglot.shapes.Cylinder;
import de.dhbw.rahmlab.openglpolyglot.shapes.Mesh;
import de.dhbw.rahmlab.openglpolyglot.shapes.RasterizedLine;
import de.dhbw.rahmlab.openglpolyglot.shapes.Polygon;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.dhbw.rahmlab.openglpolyglot.shapes.Sphere;
import de.orat.view3d.euclid3dviewapi.spi.iAABB;
import de.orat.view3d.euclid3dviewapi.spi.iEuclidViewer3D;
import java.awt.Color;
import java.util.HashMap;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.type.CTypeConversion;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

@CContext(Directives.class)
public class EuclidViewer3D implements iEuclidViewer3D {

    private HashMap<Long, Shape> nodes = new HashMap<>();
    private long nodesCount = 0L;

    @Override
    public void open() {
        addCoordinateAxes();
        OpenGLPolyglot.initialize();
    }

    @Override
    public void close() {
        nodes.clear();
        nodesCount = 0L;
        // TODO: close window
    }

    @Override
    public iAABB getAABB() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long addSphere(Point3d location, double radius, Color color, String label, boolean transparency) {
        return addNode(new Sphere(location, radius, color, label, transparency));
    }

    @Override
    public long addLine(Point3d p1, Point3d p2, Color color, double radius, String label) {
        return addNode(new Cylinder(p1, p2, color, radius, label));
    }

    public long addRasterizedLine(Point3d p1, Point3d p2, Color color, float width) {
        return addNode(new RasterizedLine(p1, p2, color, width));
    }

    @Override
    public long addArrow(Point3d location, Vector3d direction, double radius, Color color, String label) {
        return addNode(new Arrow(location, direction, radius, color, label));
    }

    @Override
    public long addCircle(Point3d location, Vector3d normal, double radius, Color color, String label, boolean isDashed, boolean isFilled) {
        return addNode(new Circle(location, normal, 20, radius, color, label, isDashed, isFilled));
    }

    @Override
    public long addPolygone(Point3d location, Point3d[] corners, Color color, String label, boolean showNormal, boolean tranparency) {
        return addNode(new Polygon(location, corners, color, label, tranparency));
    }

    @Override
    public long addCube(Point3d location, Vector3d dir, double width, Color color, String label, boolean tranparency) {
        return addNode(new Cube(location, dir, width, color, label, tranparency));
    }

    @Override
    public long addMesh(String path, Matrix4d transform) {
        var scene = AI.importFile(CTypeConversion.toCString(path).get(),
                                  AI.processPreset_TargetRealtime_MaxQuality());
        return addNode(new Mesh(scene, transform));
    }

    @Override
    public boolean removeNode(long handle) {
        var isRemoved = nodes.remove(handle) != null;
        return isRemoved;
    }

    @Override
    public void transform(long handle, Matrix4d transformMatrix) {
        var node = nodes.get(handle);
        node.transform(transformMatrix);
    }

    public HashMap<Long, Shape> getNodes() {
        return nodes;
    }

    private long addNode(Shape shape) {
        nodes.put(++nodesCount, shape);
        return nodesCount;
    }

    private void addCoordinateAxes() {
        addRasterizedLine(new Point3d(0, 0, 0), new Point3d(10, 0, 0), Color.red, 2);
	addRasterizedLine(new Point3d(0, 0, 0), new Point3d(0, 10, 0), Color.green, 2);
	addRasterizedLine(new Point3d(0, 0, 0), new Point3d(0, 0, 10), Color.blue, 2);
    }
}
