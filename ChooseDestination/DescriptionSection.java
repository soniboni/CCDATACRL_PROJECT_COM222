package ChooseDestination;

import Abstractions.ImagePanel;
import Abstractions.PageLoader;

import javax.swing.*;
import java.awt.*;

public class DescriptionSection extends ImagePanel{
    MapPage observer;
    Country currentlyDisplayed;
    JButton addButton = new JButton();
    public DescriptionSection(String imagePath, MapPage observer) {
        super(imagePath);
        this.setBackground(Color.WHITE);
        this.observer = observer;
        initializeAddButton();
    }

    public void initializeAddButton(){
        addButton.setIcon(new ImageIcon("src/Assets/Buttons/addButton.png"));
        addButton.setBounds(336, 104, 41, 30);
        addButton.setFocusable(false);
        addButton.setVisible(false);

        addButton.addActionListener(e -> addCountry());
        this.add(addButton);
    }

    public void addCountry(){
        // do some confirmation
        currentlyDisplayed.selected = true;
        addButton.setVisible(false);
        // Communicate to chosen countries section to add this country
        observer.chosenCountriesSection.addCountry(currentlyDisplayed);
    }

    public void addToScreen(Country c){
        currentlyDisplayed = c;
        this.changeBackground("src\\Assets\\CountryDescriptions\\" + c.name + ".png");
        addButton.setVisible(!c.selected);
    }
}
