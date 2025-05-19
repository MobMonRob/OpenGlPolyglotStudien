package de.dhbw.rahmlab.openglpolyglot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

/**
 * Swing Component for the EuclidViewer3D-Visualizer
 */
public class EuclidViewerComponent extends JPanel {

    private BufferedImage image;

    public EuclidViewerComponent() {

        this.image = new BufferedImage(OpenGLRenderer.INITIAL_WIDTH, OpenGLRenderer.INITIAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(OpenGLRenderer.INITIAL_WIDTH, OpenGLRenderer.INITIAL_HEIGHT));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MouseListener.mouseX.get().write(e.getX());
                MouseListener.mouseY.get().write(e.getY());
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                MouseListener.onMouseMotion(e.getX(), e.getY());
            }
        });

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                var zoomPtr = OpenGLRenderer.zoom.get();
                zoomPtr.write(zoomPtr.read() * (-0.1f*e.getWheelRotation() + 1f));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, image.getHeight(), image.getWidth(), -image.getHeight(), this);
    }
    
    public void startUpdateLoop() {
        while (this.isVisible()) {
            try {
                var width = OpenGLRenderer.width.get();
                var height = OpenGLRenderer.height.get();
                updatePixels(width.read(), height.read());
                width.write(this.getWidth());
                height.write(this.getHeight());
                Thread.sleep(1);
            } catch (InterruptedException _) {
                System.err.println("Image update loop interrupted!");
            } catch (Exception exception) {
                System.err.println(exception.getClass().getName()
                        + " in image update loop: "
                        + exception.getMessage());
            }
        }
    }

    private void updatePixels(int width, int height) {
        var pixelMap = new int[width * height * 4];

        for (int i = 0; i < pixelMap.length; i++) {
            pixelMap[i] = OpenGLRenderer.pixelMap.get().read(i);
        }

        var newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var raster = (WritableRaster) newImage.getData();
        raster.setPixels(0, 0, width, height, pixelMap);
        newImage.setData(raster);
        image = newImage;
        repaint();
    }
}
