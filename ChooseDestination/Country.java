package ChooseDestination;

import Abstractions.PageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Country extends JPanel{
    MapPage observer;
    String name;
    int x_relative, y_relative;
    boolean selected = false, onView = false;
    public Country(String name, int xr, int yr, MapPage observer){
        this.observer = observer;
        this.name = name;
        this.x_relative = xr;
        this.y_relative = yr;

        this.setOpaque(false);
        this.addMouseListener(new onClick());
        this.setVisible(true);
        this.setLayout(null);
        this.setSize(new Dimension(30, 20));
    }

    public void relocate(int imgPosX, int imgPosY){
            this.setLocation(new Point(imgPosX + x_relative, imgPosY + y_relative));
            this.repaint();
    }

    private class onClick extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e){
            if (!observer.map.countrySelectionMode) return;
            // asks screen to focus on this country
            observer.map.focusOnScreen(Country.this);
            // communicates that this country has been clicked
            observer.descriptionSection.addToScreen(Country.this);
        }
    }
}
