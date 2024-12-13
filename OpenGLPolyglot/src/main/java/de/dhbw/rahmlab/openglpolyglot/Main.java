package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import de.orat.view3d.euclid3dviewapi.api.ViewerService;
import de.orat.view3d.euclid3dviewapi.spi.iEuclidViewer3D;
import java.awt.Color;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

@CContext(Directives.class)
public class Main {

    public static void main(String[] args) throws Exception {
        IsolateSingleton.initialize();
        init();
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = IsolateSingleton.Prologue.class,
                        epilogue = CEntryPointSetup.LeaveEpilogue.class)
    public static void init() throws Exception {
        var viewerService = ViewerService.getInstance();
        var viewer = viewerService.getViewer().get();
        addExampleShapes(viewer);
        viewer.open();
    }

    private static void addExampleShapes(iEuclidViewer3D viewer) throws Exception {
	viewer.addSphere(new Point3d(-2, 2, -2), 1.5, Color.magenta, "Sphere", true);

        viewer.addLine(new Point3d(0, -5, 5), new Point3d(5, -3, 4), Color.gray, 0.1, "Line");

	viewer.addArrow(new Point3d(-2, -2, -2), new Vector3d(1, 1, 1), 0.1, Color.cyan, "Arrow");

	viewer.addCircle(new Point3d(-2, 3, 2), new Vector3d(1, 0, 0), 2.0, Color.yellow, "Circle1", false, true);
	viewer.addCircle(new Point3d(-2, 4, 3), new Vector3d(0, 1, 0), 2.0, Color.red, "Circle2", true, false);
	viewer.addCircle(new Point3d(-2, 5, 4), new Vector3d(0, -1, -1), 2.0, Color.orange, "Circle3", false, false);

	viewer.addPolygone(new Point3d(2, 2, 2), new Point3d[] {
		new Point3d(2, 2, 2),
		new Point3d(3, 3, 3),
		new Point3d(3, 0, 3),
		new Point3d(1, -1, 1)
	}, Color.blue, "Polygon", false, true);

	viewer.addCube(new Point3d(1, 0, 0), new Vector3d(0, 1, 1), 1, new Color(0, 255, 127), "Cube1", false);
	viewer.addCube(new Point3d(0, 0, 0), new Vector3d(1, 0, 1), 1, new Color(255, 127, 0), "Cube2", true);
	viewer.addCube(new Point3d(0, 0, 1), new Vector3d(1, 1, 1), 1, new Color(127, 0, 255), "Cube3", false);
    }
}
