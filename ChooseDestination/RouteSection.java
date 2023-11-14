package ChooseDestination;

import Abstractions.ImagePanel;
import Abstractions.PageLoader;
import PathDatabase.Path;
import PathDatabase.PathDatabase;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class RouteSection extends JPanel {
    MapPage observer;
    PriorityQueue<Path> routes = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
    JPanel routesDisplay;
    public RouteSection(MapPage observer){
        this.observer = observer;
        this.setBackground(Color.WHITE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setSize(new Dimension(430, 92));

        collectRoutes();
        addComponents();
    }
    public void addComponents(){
        ImagePanel sortBy = new ImagePanel("src/Assets/RouteSelection/sortBy.png");

        JButton shortestRoute = new JButton("",
                new ImageIcon("src/Assets/Buttons/shortestRoute.png"));
        shortestRoute.setFocusable(false);
        shortestRoute.setBounds(20, 16, 73, 30);
        shortestRoute.addActionListener(e -> {
            PriorityQueue<Path> newRoutes = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
            while (!routes.isEmpty()) newRoutes.add(routes.poll());
            routes = newRoutes;
            addRoutes();
            observer.resize();
        });

        JButton cheapestCost = new JButton("",
                new ImageIcon("src/Assets/Buttons/cheapestCost.png"));
        cheapestCost.setFocusable(false);
        cheapestCost.setBounds(100, 16, 45, 30);
        cheapestCost.addActionListener(e -> {
            PriorityQueue<Path> newRoutes = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
            while (!routes.isEmpty()) newRoutes.add(routes.poll());
            routes = newRoutes;
            addRoutes();
            observer.resize();
        });

        JButton fastestRoute = new JButton("",
                new ImageIcon("src/Assets/Buttons/fastestRoute.png"));
        fastestRoute.setFocusable(false);
        fastestRoute.setBounds(152, 16, 68, 30);
        fastestRoute.addActionListener(e -> {
            PriorityQueue<Path> newRoutes = new PriorityQueue<>(Comparator.comparingInt(a -> a.duration));
            while (!routes.isEmpty()) newRoutes.add(routes.poll());
            routes = newRoutes;
            addRoutes();
            observer.resize();
        });

        sortBy.add(shortestRoute);
        sortBy.add(fastestRoute);
        sortBy.add(cheapestCost);

        routesDisplay = new JPanel();
        routesDisplay.setLayout(new BoxLayout(routesDisplay, BoxLayout.Y_AXIS));
        routesDisplay.setBackground(Color.WHITE);

        ImagePanel backPanel = new ImagePanel("src/Assets/RouteSelection/backPanel.png");
        JButton back = new JButton("", new ImageIcon("src/Assets/Buttons/back.png"));
        back.setFocusable(false);
        back.setBounds(17,25,105,46);
        back.addActionListener(e -> observer.changeToChooseDestinationPage());
        backPanel.add(back);

        this.add(new ImagePanel("src/Assets/RouteSelection/header.png"));
        this.add(sortBy);
        this.add(routesDisplay);
        addRoutes();
        this.add(backPanel);
    }

    public void collectRoutes(){
        StringBuilder path = new StringBuilder();
        // COLLECTS ALL THE POSSIBLE ROUTES IN THE DATABASE AND STORES THEM LOCALLY HERE
        for (Country country: observer.chosenCountriesSection.countriesAdded){
            if (path.length() > 0) path.append(",");
            path.append(country.name);
        }

        List<Path> routes = PathDatabase.getInstance().getAllPaths(path.toString());
        this.routes.addAll(routes);
    }

    public void addRoutes(){
        routesDisplay.removeAll();
        routesDisplay.setSize(430, 0);
        createPanel();
        this.setSize(new Dimension(430, 260 + routesDisplay.getHeight()));
        this.repaint();
    }

    public void createPanel(){
        if (routes.isEmpty()) return;

        Path currentPath = routes.poll();
        ImagePanel route = new ImagePanel("src/Assets/RouteSelection/routePanel.png");

        StringBuilder routeName = new StringBuilder();
        for (String path: currentPath.path.split(",")){
            if (routeName.length() > 0) routeName.append(" > ");
            routeName.append(path.charAt(0));
            routeName.append(path.charAt(1));
        }
        // ADDS THE PARTS OF THE PANEL
        JLabel routeLabel = new JLabel(routeName.toString().toUpperCase());
        routeLabel.setForeground(new Color(38, 44, 78));
        routeLabel.setFont(new Font("Arial Nova", Font.BOLD, 25));
        routeLabel.setBounds(12, 15, 200, 44);
        route.add(routeLabel);

        JLabel routeDistance = new JLabel(String.format("%,d", currentPath.distance) + " KILOMETERS");
        routeDistance.setForeground(new Color(88, 88, 88));
        routeDistance.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        routeDistance.setBounds(13, 60, 140, 18);
        route.add(routeDistance);

        JLabel priceStartsAt = new JLabel("PRICE STARTS AT:", SwingConstants.RIGHT);
        priceStartsAt.setForeground(new Color(88, 88, 88));
        priceStartsAt.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        priceStartsAt.setBounds(180, 25, 200, 18);
        route.add(priceStartsAt);

        JLabel routeDuration = new JLabel("~ "+String.format("%,d", currentPath.duration) + " Hours");
        routeDuration.setForeground(new Color(244, 129, 52));
        routeDuration.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        routeDuration.setBounds(13, 78, 140, 18);
        route.add(routeDuration);

        JLabel routeCost = new JLabel("PHP "+String.format("%,d", currentPath.cost), SwingConstants.RIGHT);
        routeCost.setForeground(new Color(244, 129, 52));
        routeCost.setFont(new Font("Arial Nova", Font.BOLD, 17));
        routeCost.setBounds(180, 38, 200, 25);
        route.add(routeCost);

        JButton view = new JButton("", new ImageIcon("src/Assets/Buttons/view.png"));
        view.setBounds(243, 64, 68, 30);
        view.setFocusable(false);
        view.addActionListener(e -> observer.map.highlightPath(currentPath.path));
        route.add(view);

        JButton select = new JButton("", new ImageIcon("src/Assets/Buttons/select.png"));
        select.setBounds(311, 64, 68, 30);
        select.setFocusable(false);
        select.addActionListener(e -> {
            observer.dateAvailabilitySection.display(currentPath);
            observer.changeToDateAvailabilitySection();
            observer.map.highlightPath(currentPath.path);
        });
        route.add(select);

        routesDisplay.add(route);
        routesDisplay.setSize(new Dimension(430, routesDisplay.getHeight() + 124));

        createPanel();
        routes.offer(currentPath);
    }
}
