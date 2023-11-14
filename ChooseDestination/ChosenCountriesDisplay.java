package ChooseDestination;

import Abstractions.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class ChosenCountriesDisplay extends ImagePanel {
    int trueHeight = 0;
    public ChosenCountriesDisplay(List<Country> countries) {
        super("src/Assets/ChosenCountries/chosenCD.png");
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.setSize(new Dimension(430, 261));
        compose(countries);
    }

    public void compose(List<Country> countries){
        int count = 0;

        for (Country c: countries){
            ImagePanel display = new ImagePanel("src\\Assets\\ChosenCountries\\"+c.name+"_.png");
            display.setBackground(Color.WHITE);
            trueHeight = 105 + 30*(count++);
            display.setBounds(25, trueHeight - 30, 400, 30);
            this.add(display, JLayeredPane.PALETTE_LAYER);
            // ADJUST THE HEIGHT OF THIS PANEL ACCORDING TO THE AMOUNT OF DISPLAYED COUNTRIES
            if (trueHeight > 261) this.setSize(new Dimension(430, trueHeight));
        }
    }
}
