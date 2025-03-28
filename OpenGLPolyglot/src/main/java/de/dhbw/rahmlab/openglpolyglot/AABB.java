package de.dhbw.rahmlab.openglpolyglot;

import de.dhbw.rahmlab.openglpolyglot.clibraries.GL;
import de.dhbw.rahmlab.openglpolyglot.shapes.RasterizedLine;
import de.dhbw.rahmlab.openglpolyglot.shapes.Shape;
import de.dhbw.rahmlab.openglpolyglot.shapes.ShapeDrawingUtils;
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
            if (!isLineInForeground(outline.getStart(), outline.getEnd())) {
                outline.draw();
            }
        }

        var mesuringScaleX = Math.pow(10, (int) Math.log10(Math.max(maxX, -minX)) + 1);
        var mesuringScaleY = Math.pow(10, (int) Math.log10(Math.max(maxY, -minZ)) + 1);
        var mesuringScaleZ = Math.pow(10, (int) Math.log10(Math.max(maxZ, -minY)) + 1);

        var mesuringSubsections = new double[] { -1, -0.75, -0.5, -0.25, 0, 0.25, 0.5, 0.75, 1 };

        for (var subsection : mesuringSubsections) {
            drawMeasuringLines(mesuringScaleX * subsection,
                               mesuringScaleY * subsection,
                               mesuringScaleZ * subsection);
        }
    }

    private void drawMeasuringLines(double x, double y, double z) {

        var xMeasuringPoints = new Point3d[] {
            new Point3d(x, minY, minZ),
            new Point3d(x, minY, maxZ),
            new Point3d(x, maxY, minZ),
            new Point3d(x, maxY, maxZ),
        };
        var yMesuringPoints = new Point3d[] {
            new Point3d(minX, y, minZ),
            new Point3d(minX, y, maxZ),
            new Point3d(maxX, y, minZ),
            new Point3d(maxX, y, maxZ),
        };
        var zMesuringPoints = new Point3d[] {
            new Point3d(minX, minY, z),
            new Point3d(minX, maxY, z),
            new Point3d(maxX, minY, z),
            new Point3d(maxX, maxY, z),
        };

        if (x >= minX && x <= maxX) drawMeasuringLines(x, xMeasuringPoints);
        if (y >= minY && y <= maxY) drawMeasuringLines(y, yMesuringPoints);
        if (z >= minZ && z <= maxZ) drawMeasuringLines(z, zMesuringPoints);
    }

    private void drawMeasuringLines(double measurement, Point3d[] measuringPoints) {

        var mesuringLines = new RasterizedLine[] {
            new RasterizedLine(measuringPoints[0], measuringPoints[1], Color.black, 1),
            new RasterizedLine(measuringPoints[0], measuringPoints[2], Color.black, 1),
            new RasterizedLine(measuringPoints[1], measuringPoints[3], Color.black, 1),
            new RasterizedLine(measuringPoints[2], measuringPoints[3], Color.black, 1),
        };

        for (var line : mesuringLines) {
            if (!isLineInForeground(line.getStart(), line.getEnd())) {
                line.draw();
            }
        }

        for (var point : measuringPoints) {
            if (!isLineInForeground(point, point)) {
                GL.translated(point.x, point.y, point.z);
                ShapeDrawingUtils.drawLabel(measurement + "");
                GL.translated(-point.x, -point.y, -point.z);
            }
        }
    }

    private boolean isLineInForeground(Point3d start, Point3d end) {
        var xRotation = OpenGLRenderer.xRotation.get().read();
        var yRotation = OpenGLRenderer.yRotation.get().read();

        return (isInRange(xRotation,   0, 180) && isEqual(start.y, end.y, maxY))
            //     => Decke ist im Vordergrund & Linie ist Decken-Linie
            || (isInRange(xRotation, 180, 360) && isEqual(start.y, end.y, minY))
            //     => Boden ist im Vordergrund & Linie ist Boden-Linie
            || (isInRange(yRotation,   0,  90) && (isEqual(start.z, end.z, maxZ) || isEqual(start.x, end.x, minX)))
            //     => vordere xy-Ebene und hintere yz-Ebene im Vordergrund & Linie ist in einer der Beiden Ebenen
            || (isInRange(yRotation,  90, 180) && (isEqual(start.x, end.x, minX) || isEqual(start.z, end.z, minZ)))
            //     => hintere yz-Ebene und vordere xy-Ebene im Vordergrund & Linie ist in einer der Beiden Ebenen
            || (isInRange(yRotation, 180, 270) && (isEqual(start.z, end.z, minZ) || isEqual(start.x, end.x, maxX)))
            //     => hintere xy-Ebene und vordere yz-Ebene im Vordergrund & Linie ist in einer der Beiden Ebenen
            || (isInRange(yRotation, 270, 360) && (isEqual(start.x, end.x, maxX) || isEqual(start.z, end.z, maxZ)));
            //     => vordere yz-Ebene und vordere xy-Ebene im Vordergrund & Linie ist in einer der Beiden Ebenen
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

    private static boolean isInRange(double value, double start, double end) {
        return value >= start && value < end;
    }

    private static boolean isEqual(double... values) {
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[0]) {
                return false;
            }
        }
        return true;
    }
}
