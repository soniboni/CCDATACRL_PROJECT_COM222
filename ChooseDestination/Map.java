package ChooseDestination;

import Abstractions.PageLoader;
import PathDatabase.PathDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.QuadCurve2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Map extends JPanel {
    private MapPage observer;
    private ImageIcon image;
    private final HashMap<String, Country> countryCollisionBoxes = new HashMap<>();
    private final HashMap<String, int[]> paths = new HashMap<>();
    ArrayList<String> edgesToDraw = new ArrayList<>();
    // POSITIONAL VALUES
    int offsetX = 0, offsetY = 0;
    int x_pos = -908, y_pos = -223;
    // STATES
    boolean move = false;
    boolean countrySelectionMode = true;
    public Map(MapPage observer) {
        this.observer = observer;
        // Load the background image
        image = new ImageIcon("src/Assets/Maps.png");
        Image scaledImage = image.getImage().getScaledInstance(1393, 755, Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);

        setOpaque(true);
        setLayout(null);
        repaint();
        initializeCountryCollisions();
        initializeRouteEdges();

        this.setBackground(Color.WHITE);
        this.addMouseListener(new MouseClickable());
        this.addMouseMotionListener(new MouseDraggable());
    }
    // ====================================== COUNTRY SELECTION MODE METHODS ======================================
    private void initializeCountryCollisions(){
        try (FileReader fileReader = new FileReader("src/ChooseDestination/CountryPositionData")){

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Country country = new  Country(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), observer);
                countryCollisionBoxes.put(data[0], country);
                this.add(country);
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        observer.chosenCountriesSection.initializeCountries(this.countryCollisionBoxes);
        countryCollisionBoxes.get("philippines").selected = true;
    }

    private  boolean isInImage(int x, int y){
        return (x > x_pos && x < x_pos + image.getIconWidth() && y > y_pos && y < y_pos + image.getIconHeight());
    }

    private void moveInsideScreen(){
        x_pos = Math.max(x_pos, -1003);
        x_pos = Math.min(x_pos, 0);

        y_pos= Math.max(y_pos, -470);
        y_pos = Math.min(y_pos, 0);
    }

    private void adjustObjects(){
        for (Country country : countryCollisionBoxes.values()) country.relocate(x_pos, y_pos);
        //for (PathImage pathImage : paths.values()) pathImage.relocate(x_pos, y_pos);
    }

    public void focusOnScreen(Country toFocus){
        x_pos = 185 - toFocus.x_relative;
        y_pos = 142 - toFocus.y_relative;
    }
    // ====================================== ROUTE SELECTION MODE METHODS ======================================
    public void initializeRouteEdges(){
        try (FileReader fileReader = new FileReader("src/ChooseDestination/PathData")){

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                paths.put(data[0]+","+data[1], new int[]{countryCollisionBoxes.get(data[0]).x_relative,
                                          countryCollisionBoxes.get(data[0]).y_relative,
                                          Integer.parseInt(data[2]),
                                          Integer.parseInt(data[3]),
                                          countryCollisionBoxes.get(data[1]).x_relative,
                                          countryCollisionBoxes.get(data[1]).y_relative,
                                         });
            }

            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void highlightPath(String path){
        edgesToDraw.clear();
        String[] countries = path.split(",");

        for (int i = 0; i < countries.length - 1; i++) {
            if (paths.containsKey(countries[i] + "," + countries[i + 1]))
                edgesToDraw.add(countries[i] + "," + countries[i + 1]);
            else edgesToDraw.add(countries[i + 1] + "," + countries[i]);
        }
    }
    // ====================================== METHODS FOR MOVING THE IMAGE ======================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        //moveInsideScreen();
        adjustObjects();
        image.paintIcon(this, g, x_pos, y_pos);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(231, 127, 64));
        g2d.setStroke(new BasicStroke(3.0f));

        for (String edge: edgesToDraw){
            int[] points = paths.get(edge);
            QuadCurve2D curve = new QuadCurve2D.Float();
            curve.setCurve(x_pos + points[0] + 15, y_pos + points[1] + 10,
                          x_pos + points[2], y_pos + points[3],
                           x_pos + points[4] + 15, y_pos + points[5] + 10);
            g2d.draw(curve);
        }

        repaint();
    }

    private class MouseClickable extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            offsetX = e.getX();
            offsetY = e.getY();

            move = isInImage(offsetX, offsetY);
        }
    }

    private class MouseDraggable extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            if (!move) return;

            x_pos += e.getX() - offsetX;
            y_pos += e.getY() - offsetY;

            offsetX = e.getX();
            offsetY = e.getY();
            repaint();
        }
    }
}