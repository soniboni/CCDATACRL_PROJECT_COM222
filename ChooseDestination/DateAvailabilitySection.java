package ChooseDestination;

import Abstractions.ImagePanel;
import Abstractions.PageLoader;
import PathDatabase.Path;
import PathDatabase.PathDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DateAvailabilitySection extends JPanel {
    MapPage observer;
    ImagePanel back;
    JPanel timeSection = new JPanel();
    String route;
    public DateAvailabilitySection(MapPage observer){
        this.observer = observer;
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.WHITE);
        timeSection.setBorder(BorderFactory.createLineBorder(new Color(88, 88, 88), 1));
        this.initializeComponents();
    }

    public void initializeComponents(){
        ImagePanel header = new ImagePanel("src/Assets/ChosenCountries/timeAH.png");

        back = new ImagePanel("src/Assets/ChosenCountries/timeAB.png");
        JButton backButton = new JButton("", new ImageIcon("src/Assets/Buttons/back.png"));
        backButton.setFocusable(false);
        backButton.setBounds(17,25,105,46);
        backButton.addActionListener(e -> {
            observer.changeToRouteSelectionPage();
        });
        back.add(backButton);

        JButton select = new JButton("", new ImageIcon("src/Assets/Buttons/selectBigger.png"));
        select.setBounds(301, 25, 105, 46);
        select.setFocusable(false);
        select.addActionListener(e ->{
            String[] r = route.split(",");
            List<Path> p = new ArrayList<>();
            HashMap<String, Path> edges = PathDatabase.getInstance().getAllEdges();
            for (int i = 0; i < r.length - 1; i++) p.add(edges.get(r[i] + " to " + r[i + 1]));
            PageLoader.getInstance().changeToSeatTypePage(p);
        });
        back.add(select);

        this.add(header);
        this.add(timeSection);
        this.add(back);
    }

    public void display(Path path){
        StringBuilder routeName = new StringBuilder();
        for (String country: path.path.split(",")){
            if (routeName.length() > 0) routeName.append(" > ");
            routeName.append(country, 0, 2);
        }

        route = path.path;
        // add section
        timeSection.removeAll();
        timeSection.setBackground(Color.WHITE);
        timeSection.setLayout(new BoxLayout(timeSection, BoxLayout.Y_AXIS));

        JPanel routeDescription = new JPanel();
        routeDescription.setLayout(null);
        routeDescription.setBackground(Color.WHITE);
        routeDescription.setPreferredSize(new Dimension(390, 102));

        // ADDS THE PARTS OF THE PANEL
        JLabel routeLabel = new JLabel(routeName.toString().toUpperCase());
        routeLabel.setForeground(new Color(38, 44, 78));
        routeLabel.setFont(new Font("Arial Nova", Font.BOLD, 30));
        routeLabel.setBounds(11, 25, 225, 44);
        routeDescription.add(routeLabel);

        JLabel routeDistance = new JLabel(String.format("%,d", path.distance) + " KILOMETERS");
        routeDistance.setForeground(new Color(88, 88, 88));
        routeDistance.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        routeDistance.setBounds(12, 60, 140, 18);
        routeDescription.add(routeDistance);

        JLabel priceStartsAt = new JLabel("PRICE STARTS AT:", SwingConstants.RIGHT);
        priceStartsAt.setForeground(new Color(88, 88, 88));
        priceStartsAt.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        priceStartsAt.setBounds(180, 25, 200, 18);
        routeDescription.add(priceStartsAt);

        JLabel routeDuration = new JLabel("~ "+String.format("%,d", path.duration) + " Hours");
        routeDuration.setForeground(new Color(244, 129, 52));
        routeDuration.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        routeDuration.setBounds(12, 76, 140, 18);
        routeDescription.add(routeDuration);

        JLabel routeCost = new JLabel("PHP "+String.format("%,d", path.cost), SwingConstants.RIGHT);
        routeCost.setForeground(new Color(244, 129, 52));
        routeCost.setFont(new Font("Arial Nova", Font.BOLD, 17));
        routeCost.setBounds(180, 38, 200, 25);
        routeDescription.add(routeCost);

        timeSection.add(routeDescription);
        timeSection.setSize(new Dimension(390, 0));
        addFlights(path);

        this.setSize(new Dimension(430, 270 + timeSection.getHeight()));
        this.repaint();
    }

    public void addFlights(Path path){
        int flightNumber = 1;

        for (String s: path.path.split(",")) {
            ImagePanel panel = new ImagePanel("src/Assets/Informations/flightSnippet.png");

            JLabel flightNumberLabel = new JLabel(String.valueOf(flightNumber++));
            flightNumberLabel.setFont(new Font("Arial Nova", Font.BOLD, 14));
            flightNumberLabel.setForeground(new Color(38, 44, 78));
            flightNumberLabel.setBounds(60, 0, 142, 18);
            panel.add(flightNumberLabel);

            JLabel pathLabel = new JLabel("paht label", SwingConstants.RIGHT);
            pathLabel.setFont(new Font("Arial Nova", Font.PLAIN, 12));
            pathLabel.setForeground(new Color(244, 129, 52));
            pathLabel.setBounds(170, 20, 200, 18);
            panel.add(pathLabel);

            ImagePanel airlineType = new ImagePanel("src/Assets/AirlineLogos/" + s + "_airline.png");
            airlineType.setBounds(180, 35, 200, 18);
            airlineType.setBackground(Color.WHITE);
            panel.add(airlineType);

            timeSection.add(panel);
            timeSection.setSize(new Dimension(430, timeSection.getHeight() + 90));
        }

        timeSection.repaint();
    }
}
