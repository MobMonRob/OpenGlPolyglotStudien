package de.dhbw.rahmlab.openglpolyglot;

import com.oracle.svm.core.c.function.CEntryPointOptions;
import com.oracle.svm.core.c.function.CEntryPointSetup;
import de.dhbw.rahmlab.openglpolyglot.Mouse;
import de.dhbw.rahmlab.openglpolyglot.swing.ImagePanel;
import de.orat.view3d.euclid3dviewapi.api.ViewerService;
import de.orat.view3d.euclid3dviewapi.spi.iEuclidViewer3D;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JFrame;
import org.graalvm.nativeimage.c.CContext;
import org.graalvm.nativeimage.c.function.CEntryPoint;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;

@CContext(Directives.class)
public class Main {

    public static void main(String[] args) {
        IsolateSingleton.initialize();
        new Thread(Main::initializeSwingFrame).start();
        init();
    }

    private static void initializeSwingFrame() {
        var frame = new JFrame("OpenGLPolyglot");
        frame.setSize(OpenGLPolyglot.INITIAL_WIDTH, OpenGLPolyglot.INITIAL_HEIGHT);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        var imagePanel = new ImagePanel();
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Mouse.mouseX.get().write(e.getX());
                Mouse.mouseY.get().write(e.getY());
            }
        });
        imagePanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Mouse.onMouseMotion(e.getX(), e.getY());
            }
        });
        imagePanel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                var scalePtr = OpenGLPolyglot.scale.get();
                scalePtr.write(scalePtr.read() * (-0.1f*e.getWheelRotation() + 1f));
            }
        });
        frame.add(imagePanel);
        frame.setVisible(true);

        while (frame.isVisible()) {
            try {
                imagePanel.updatePixels();
                OpenGLPolyglot.width.get().write(frame.getWidth());
                OpenGLPolyglot.height.get().write(frame.getHeight());
                Thread.sleep(10);
            } catch (Exception exception) {
                System.err.println("Exception in UI loop: " + exception.getMessage());
            }
        }
    }

    @CEntryPoint
    @CEntryPointOptions(prologue = IsolateSingleton.Prologue.class,
                        epilogue = CEntryPointSetup.LeaveEpilogue.class)
    public static void init() {
        var viewerService = ViewerService.getInstance();
        var viewer = viewerService.getViewer().get();
        addExampleShapes(viewer);
        
        try {
            viewer.open();
        } catch (Exception exception) {
            System.err.println("Could not initialize viewer! " + exception.getMessage());
        }
    }

    private static void addExampleShapes(iEuclidViewer3D viewer) {
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
