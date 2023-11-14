package ChooseDestination;

import Abstractions.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class MapPage extends ImagePanel {
    DescriptionSection descriptionSection =
            new DescriptionSection("src/Assets/CountryDescriptions/philippines.png", this);
    ChosenCountriesSection chosenCountriesSection = new ChosenCountriesSection(this);
    Map map = new Map(this);
    JPanel nextPanel = new JPanel();
    ChosenCountriesDisplay chosenCountriesDisplay;
    RouteSection routeSection;
    DateAvailabilitySection dateAvailabilitySection = new DateAvailabilitySection(this);
    public MapPage(){
        super("src/Assets/CDBackground.png");
        // sets the frame
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        addNextPanel();
        addMapSection();

        dateAvailabilitySection.setLocation(new Point(0, 576));
        chosenCountriesSection.setLocation(new Point(0, 841));
        changeToChooseDestinationPage();

        resize();
    }

    public void addMapSection() {
        map.setBounds(30, 280, 370, 285);
        this.add(map);

        descriptionSection.setBounds(20, 588, 391, 143);
        this.add(descriptionSection);
    }
    // ================================= CHOOSE DESTINATION METHODS ==================================

    public void addNextPanel(){
        nextPanel.setBackground(Color.WHITE);
        nextPanel.setLayout(null);

        JButton nextButton = new JButton();
        nextButton.setIcon(new ImageIcon("src/Assets/Buttons/next.png"));
        nextButton.setFocusable(false);
        nextButton.setBounds(303, 54, 105, 46);
        nextButton.addActionListener(e -> {
            changeToRouteSelectionPage();
        });

        ImagePanel text = new ImagePanel("src/Assets/ChosenCountries/next description.png");
        text.setBounds(259, 103, 149, 16);

        nextPanel.add(nextButton);
        nextPanel.add(text);
    }
    // =================================== ROUTE SELECTION METHODS ======================================
    public void addChosenCountriesDisplay(){
        chosenCountriesDisplay = new ChosenCountriesDisplay(chosenCountriesSection.countriesAdded);
        chosenCountriesDisplay.setBounds(0, 580, 430,  chosenCountriesDisplay.getHeight());
        this.add(chosenCountriesDisplay, JLayeredPane.PALETTE_LAYER);
    }
    public void addRoutesSection(){
        routeSection = new RouteSection(this);
        routeSection.setBounds(0, 620 + chosenCountriesDisplay.trueHeight, 430, routeSection.getHeight());
        this.add(routeSection, JLayeredPane.MODAL_LAYER);
    }

    // =========================== METHODS FOR CHANGING THE SCREEN =======================================

    public void changeToChooseDestinationPage(){
        if (chosenCountriesDisplay != null) this.remove(chosenCountriesDisplay);
        if (routeSection != null) this.remove(routeSection);
        if (dateAvailabilitySection != null) this.remove(dateAvailabilitySection);
        map.countrySelectionMode = true;
        map.edgesToDraw.clear();

        this.add(nextPanel);
        this.add(chosenCountriesSection);

        this.resize();
        this.repaint();
    }
    public void changeToRouteSelectionPage(){
        this.remove(chosenCountriesSection);
        this.remove(nextPanel);
        this.remove(dateAvailabilitySection);
        map.countrySelectionMode = false;

        addChosenCountriesDisplay();
        addRoutesSection();

        this.resize();
        this.repaint();
    }

    public void changeToDateAvailabilitySection(){
        this.remove(chosenCountriesDisplay);
        this.remove(routeSection);
        map.countrySelectionMode = false;

        this.add(dateAvailabilitySection, JLayeredPane.MODAL_LAYER);
        this.setPreferredSize(new Dimension(430, 576 + dateAvailabilitySection.getHeight()));
        this.revalidate();
    }
    public void resize(){
        // We compute the additional height for the added chosen countries
        // + the next button if it will be included.
        // Each country have a space of 54 px and the next button have a space of 137
        if (map.countrySelectionMode) {
            int approximateHeight = 54 * chosenCountriesSection.countriesAdded.size();
            nextPanel.setBounds(0, 844 + approximateHeight, 430, 137);

            approximateHeight = Math.max(148, approximateHeight + 170);
            this.setPreferredSize(new Dimension(430, 844 + approximateHeight));
            chosenCountriesSection.setSize(430, approximateHeight);

            chosenCountriesSection.revalidate();
            nextPanel.setVisible(chosenCountriesSection.countriesAdded.size() > 0);
        }
        else{
            int height = routeSection.getY() + routeSection.getHeight();
            this.setPreferredSize(new Dimension(430, height));
        }

        this.revalidate();
    }
}
