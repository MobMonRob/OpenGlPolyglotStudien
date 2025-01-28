package de.dhbw.rahmlab.openglpolyglot.swing;

import de.dhbw.rahmlab.openglpolyglot.OpenGLPolyglot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        image = new BufferedImage(OpenGLPolyglot.INITIAL_WIDTH, OpenGLPolyglot.INITIAL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(image, 0, image.getHeight(), image.getWidth(), -image.getHeight(), this);
    }

    public void updatePixels() {
        var width = OpenGLPolyglot.width.get().read();
        var height = OpenGLPolyglot.height.get().read();
        var pixelMap = new int[width * height * 4];

        for (int i = 0; i < pixelMap.length; i++) {
            pixelMap[i] = OpenGLPolyglot.pixelMap.get().read(i);
        }

        var newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        var raster = (WritableRaster) newImage.getData();
        raster.setPixels(0, 0, width, height, pixelMap);
        newImage.setData(raster);
        image = newImage;
        repaint();
    }
}
