package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.shapes.RasterizedLine;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.orat.view3d.euclid3dviewapi.spi.iAABB;
import de.orat.view3d.euclid3dviewapi.util.CutFailedException;
import de.orat.view3d.euclid3dviewapi.util.Line;
import de.orat.view3d.euclid3dviewapi.util.Plane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

public class AABB implements iAABB {

    private double minX, minY, minZ;
    private double maxX, maxY, maxZ;
    private RasterizedLine[] outlines;

    public AABB(double minX, double minY, double minZ,
                double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.outlines = calculateOutlines();
    }

    public AABB(Collection<Shape> shapes) {
        calculateAABBFor(shapes);
    }

    public final void calculateAABBFor(Collection<Shape> shapes) {
        var nodeAABBs = shapes.stream().map(node -> node.getAABB()).toList();
        minX = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMinX()).min().orElse(0);
        minY = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMinY()).min().orElse(0);
        minZ = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMinZ()).min().orElse(0);
        maxX = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMaxX()).max().orElse(0);
        maxY = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMaxY()).max().orElse(0);
        maxZ = nodeAABBs.stream().mapToDouble(nodeAABB -> nodeAABB.getMaxZ()).max().orElse(0);
        outlines = calculateOutlines();
    }

    @Override
    public Point3d[] clip(Plane plane) {

        var points = new ArrayList<Vector3d>();

        for (RasterizedLine outline : outlines) {

            var start = outline.getStart();
            var end = outline.getEnd();
            var direction = new Vector3d(end.x - start.x, end.y - start.y, end.z - start.z);
            var line = new Line(new Vector3d(start), direction);

            try {
                points.addAll(List.of(plane.cut(line)));
            } catch (CutFailedException ex) {
            }
        }

        return points.stream()
                .map(vector -> new Point3d(vector))
                .toArray(size -> new Point3d[size]);
    }

    @Override
    public Point3d[] clip(Line line) {

        var points = new ArrayList<Vector3d>();
        var corners = getCorners();
        var planes = new Plane[] {
            new Plane(corners[0], corners[1], corners[2]),
            new Plane(corners[0], corners[1], corners[4]),
            new Plane(corners[0], corners[2], corners[4]),
            new Plane(corners[7], corners[3], corners[5]),
            new Plane(corners[7], corners[3], corners[6]),
            new Plane(corners[7], corners[5], corners[6]),
        };

        for (var plane : planes) {
            try {
                points.addAll(List.of(plane.cut(line)));
            } catch (CutFailedException ex) {
            }
        }

        return points.stream()
                .map(vector -> new Point3d(vector))
                .toArray(size -> new Point3d[size]);
    }

    public void draw() {
        for (RasterizedLine outline : outlines) {
            outline.draw();
        }
    }

    private RasterizedLine[] calculateOutlines() {
        var corners = getCorners();

        return new RasterizedLine[] {
            new RasterizedLine(corners[0], corners[1], Color.black, 1),
            new RasterizedLine(corners[0], corners[2], Color.black, 1),
            new RasterizedLine(corners[0], corners[4], Color.black, 1),
            new RasterizedLine(corners[1], corners[3], Color.black, 1),
            new RasterizedLine(corners[1], corners[5], Color.black, 1),
            new RasterizedLine(corners[2], corners[3], Color.black, 1),
            new RasterizedLine(corners[2], corners[6], Color.black, 1),
            new RasterizedLine(corners[3], corners[7], Color.black, 1),
            new RasterizedLine(corners[4], corners[5], Color.black, 1),
            new RasterizedLine(corners[4], corners[6], Color.black, 1),
            new RasterizedLine(corners[5], corners[7], Color.black, 1),
            new RasterizedLine(corners[6], corners[7], Color.black, 1)
        };
    }

    private Point3d[] getCorners() {
        return new Point3d[] {
            new Point3d(minX, minY, minZ),
            new Point3d(minX, minY, maxZ),
            new Point3d(minX, maxY, minZ),
            new Point3d(minX, maxY, maxZ),
            new Point3d(maxX, minY, minZ),
            new Point3d(maxX, minY, maxZ),
            new Point3d(maxX, maxY, minZ),
            new Point3d(maxX, maxY, maxZ)
        };
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }
}
