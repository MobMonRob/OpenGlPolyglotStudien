package de.dhbw.rahmlab.openglpolyglot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

public class EuclidViewerComponent extends JPanel {

    private BufferedImage image;
    private int mouseX;
    private int mouseY;

    public EuclidViewerComponent() {

        this.image = new BufferedImage(OpenGLRenderer.INITIAL_WIDTH, OpenGLRenderer.INITIAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(OpenGLRenderer.INITIAL_WIDTH, OpenGLRenderer.INITIAL_HEIGHT));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                var xRotationPtr = OpenGLRenderer.xRotation.get();
                var yRotationPtr = OpenGLRenderer.yRotation.get();
                var deltaX = e.getX() - mouseX;
                var deltaY = e.getY() - mouseY;
                // rotiere entlang der Y-Achse, wenn sich Maus in X-Richtung bewegt
                xRotationPtr.write((deltaY + xRotationPtr.read() + 360) % 360);
                // rotiere entlang der X-Achse, wenn sich Maus in Y-Richtung bewegt
                yRotationPtr.write((deltaX + yRotationPtr.read() + 360) % 360);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                var scalePtr = OpenGLRenderer.scale.get();
                scalePtr.write(scalePtr.read() * (-0.1f*e.getWheelRotation() + 1f));
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
                Thread.sleep(10);
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
