package InformationSection;


import Abstractions.ImagePanel;
import Abstractions.PageLoader;
import PathDatabase.Path;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Locale;


public class AirplaneSeatType extends ImagePanel {
    private List<Path> path;
    
    private JPanel panelContainer;
    private ImagePanel back;
    public AirplaneSeatType(List<Path> path) {
        super("src/Assets/Informations/seatTypeBG.png");
        this.path = path;
        this.setLayout(null);

        initComponents();
        addFlights();
        this.repaint();
        this.revalidate();
    }

    private void initComponents() {
        StringBuilder routeName = new StringBuilder();
        for (Path p: path){
            if (routeName.length() > 0) routeName.append(" > ");
            routeName.append(p.path.split(" ")[0], 0, 2);
        }

        // ADDS THE PARTS OF THE PANEL
        JLabel routeLabel = new JLabel(routeName.toString().toUpperCase());
        routeLabel.setForeground(new Color(38, 44, 78));
        routeLabel.setFont(new Font("Arial Nova", Font.BOLD, 35));
        routeLabel.setBounds(20, 230, 300, 44);
        this.add(routeLabel);

        panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        panelContainer.setBounds(0, 290, 440, 0);

        JScrollPane scrollPane = new JScrollPane(panelContainer);
        scrollPane.setBounds(0, 290, 430, 540); // Adjusted width to 415
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        back = new ImagePanel("src/Assets/Informations/back.png");
        back.setBounds(0, 830, 430, 116);
        JButton backButton = new JButton("", new ImageIcon("src/Assets/Buttons/back.png"));
        backButton.setFocusable(false);
        backButton.setBounds(20, 29, 105, 46);
        backButton.addActionListener(e -> {
            PageLoader.getInstance().changeToMapPage();
        });
        back.add(backButton);

        JButton next = new JButton("", new ImageIcon("src/Assets/Buttons/next.png"));
        next.setFocusable(false);
        next.setBounds(306, 29, 105, 46);
        next.addActionListener(e -> {
            Passenger.setCurrentPath(path);
            PageLoader.getInstance().changeToPassengerDetails();
        });
        back.add(next);

        this.add(scrollPane);
        this.add(back);
    }

    private void addFlights() {
        int count = 0;
        for (Path c: path) addFlight(count++ + 1, c);
    }

    private void addFlight(int flightNumber, Path p) {
        ImagePanel panel = new ImagePanel("src/Assets/Informations/panel.png");

        JLabel flightNumberLabel = new JLabel(String.valueOf(flightNumber));
        flightNumberLabel.setFont(new Font("Arial Nova", Font.BOLD, 14));
        flightNumberLabel.setForeground(new Color(38, 44, 78));
        flightNumberLabel.setBounds(55, 9, 142, 18);
        panel.add(flightNumberLabel);

        JLabel pathLabel = new JLabel(p.path.toLowerCase(), SwingConstants.RIGHT);
        pathLabel.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        pathLabel.setForeground(new Color(244, 129, 52));
        pathLabel.setBounds(200, 28, 200, 18);
        panel.add(pathLabel);

        ImagePanel airlineType = new ImagePanel("src/Assets/AirlineLogos/" + p.path.split(" ")[0] + "_airline.png");
        airlineType.setBounds(210, 55, 350, 18);
        airlineType.setBackground(Color.WHITE);
        panel.add(airlineType);

        JLabel type = new JLabel("Economy", SwingConstants.RIGHT);
        type.setFont(new Font("Arial Nova", Font.BOLD, 14));
        type.setForeground(new Color(88, 88, 88));
        type.setBounds(200, 164, 200, 18);
        panel.add(type);

        JLabel price = new JLabel(String.format("%,d", p.cost) , SwingConstants.RIGHT);
        price.setFont(new Font("Arial Nova", Font.PLAIN, 13));
        price.setForeground(new Color(244, 129, 52));
        price.setBounds(200, 187, 200, 18);
        panel.add(price);

        JComboBox<String> seatTypeComboBox = new JComboBox<>(new String[]{"Economy Class", "Business Class", "First Class"});
        seatTypeComboBox.setBounds(15, 95, 390, 60);
        seatTypeComboBox.addItemListener(e -> {
            switch (seatTypeComboBox.getSelectedIndex()){
                case 0 -> {
                    type.setText("Economy");
                    price.setText(String.format("%,d", p.cost));
                }
                case 1 -> {
                    type.setText("Business");
                    price.setText(String.format("%,.3f", p.cost * 1.72));
                }
                case 2 -> {
                    type.setText("First Class");
                    price.setText(String.format("%,.3f", p.cost * 2.83));
                }
            }

            this.repaint();
        });

        panel.add(seatTypeComboBox);

        panelContainer.add(panel);
        panel.setSize(new Dimension(430, panelContainer.getHeight() + 235));

        panelContainer.revalidate();
        this.repaint();
    }
}
