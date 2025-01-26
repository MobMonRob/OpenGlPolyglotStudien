package de.dhbw.rahmlab.openglpolyglot.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel() {
        image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 800, 800, -800, this);
    }

    public void setPixels(int[] pixelMap) {
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0, 0, 800, 800, pixelMap);
        image.setData(raster);
        repaint();
    }
}
