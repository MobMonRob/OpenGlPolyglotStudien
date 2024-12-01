package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.shapes.Arrow;
import de.dhbw.rahmlab.openglpolyglot.shapes.Circle;
import de.dhbw.rahmlab.openglpolyglot.shapes.Cube;
import de.dhbw.rahmlab.openglpolyglot.shapes.Line;
import de.dhbw.rahmlab.openglpolyglot.shapes.Polygon;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.dhbw.rahmlab.openglpolyglot.shapes.Sphere;
import de.orat.view3d.euclid3dviewapi.spi.iAABB;
import de.orat.view3d.euclid3dviewapi.spi.iEuclidViewer3D;
import java.awt.Color;
import java.util.HashMap;
import org.jogamp.vecmath.Matrix4d;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class EuclidViewer3D implements iEuclidViewer3D {

    private HashMap<Long, Shape> nodes;
    private long nodesCount;

    @Override
    public void open() {
        nodes = new HashMap<>();
        nodesCount = 0L;
    }

    @Override
    public void close() {
        nodes.clear();
        nodesCount = 0L;
    }

    @Override
    public iAABB getAABB() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long addSphere(Point3d location, double radius, Color color, String label, boolean transparency) {
        return addNode(new Sphere(location, radius, color));
    }

    @Override
    public long addLine(Point3d p1, Point3d p2, Color color, double radius, String label) {
        return addNode(new Line(p1, p2, color, (float) radius));
    }

    @Override
    public long addArrow(Point3d location, Vector3d direction, double radius, Color color, String label) {
        return addNode(new Arrow(location, direction, radius, color));
    }

    @Override
    public long addCircle(Point3d location, Vector3d normal, double radius, Color color, String label, boolean isDashed, boolean isFilled) {
        return addNode(new Circle(location, normal, 20, radius, color, isDashed, isFilled));
    }

    @Override
    public long addPolygone(Point3d location, Point3d[] corners, Color color, String label, boolean showNormal, boolean tranparency) {
        return addNode(new Polygon(corners, color));
    }

    @Override
    public long addCube(Point3d location, Vector3d dir, double width, Color color, String label, boolean tranparency) {
        return addNode(new Cube(location, dir, width, color));
    }

    @Override
    public long addMesh(String path, Matrix4d transform) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeNode(long handle) {
        var isRemoved = nodes.remove(handle) != null;
        return isRemoved;
    }

    @Override
    public void transform(long handle, Matrix4d transform) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public HashMap<Long, Shape> getNodes() {
        return nodes;
    }

    private long addNode(Shape shape) {
        nodes.put(++nodesCount, shape);
        return nodesCount;
    }
}
