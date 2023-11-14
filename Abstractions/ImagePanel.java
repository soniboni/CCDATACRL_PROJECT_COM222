package Abstractions;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JLayeredPane {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        // Load the background image
        backgroundImage = new ImageIcon(imagePath).getImage();

        // Set the preferred size of the panel to match the image size
        Dimension size = new Dimension(backgroundImage.getWidth(null),
                backgroundImage.getHeight(null));
        this.setPreferredSize(new Dimension(size));
        setOpaque(true);
        setVisible(true);
        setLayout(null);
        repaint();
    }

    public void changeBackground(String imagePath){
        this.backgroundImage = new ImageIcon(imagePath).getImage();
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, null);
    }
}
