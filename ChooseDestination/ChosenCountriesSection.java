package ChooseDestination;

import Abstractions.ImagePanel;
import Abstractions.PageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChosenCountriesSection extends JPanel {
    MapPage observer;
    HashMap<Country, ImagePanel> countryList = new HashMap<>();
    List<Country> countriesAdded = new ArrayList<>();
    public ChosenCountriesSection(MapPage observer){
        this.observer = observer;
        this.setPreferredSize(new Dimension(430, -1));
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.WHITE);
    }

    public void initializeCountries(HashMap<String, Country> countries){
        for (String c: countries.keySet()){
            ImagePanel country = new ImagePanel(
                                "src\\Assets\\ChosenCountries\\" + c + ".png");

            JButton removeButton = new JButton();
            removeButton.setIcon(new ImageIcon("src/Assets/Buttons/removeButton.png"));

            removeButton.addActionListener(e -> {
                removeCountry(countries.get(c));
            });

            removeButton.setFocusable(false);
            removeButton.setBounds(14, 12, 22 ,22);
            country.add(removeButton);

            countryList.put(countries.get(c), country);
        }
    }

    public void addCountry(Country country){
        this.add(countryList.get(country));
        countriesAdded.add(country);
        observer.resize();
        this.revalidate();
    }

    public void removeCountry(Country country){
        this.remove(countryList.get(country));
        country.selected = false;
        countriesAdded.remove(country);
        observer.resize();
        this.repaint();
        this.revalidate();
    }
}
