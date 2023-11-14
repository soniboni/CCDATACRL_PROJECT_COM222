package InformationSection;

import Abstractions.PageLoader;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;

public class BaggageAdd extends JPanel {

    private static final long serialVersionUID = 1L;
    private JList<String> list;
    private DefaultListModel<String> listModel;
    private Hashtable<String, Double> baggagePrices;
    private JLabel totalPriceLabel;
    private Hashtable<String, String> passengerInfo; 

    // currency
    double currencyPH = 1;

    // prices
    double handcarryPrice = 49.00 * currencyPH;
    double standardPrice = 69.00 * currencyPH;
    double heavyPrice = 89.00 * currencyPH;
    double overweightPrice = 129.00 * currencyPH;

    public BaggageAdd(Hashtable<String, String> passengerInfo) {  
    	
        baggagePrices = new Hashtable<>();
        baggagePrices.put("Handcarry", handcarryPrice);
        baggagePrices.put("Standard", standardPrice);
        baggagePrices.put("Heavy", heavyPrice);
        baggagePrices.put("Oversized", overweightPrice);

        setBounds(0, 0, 430, 930);
        setLayout(null);
		
		JButton backButton = new JButton("Back");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BaggagePage baggagePanel = new BaggagePage();
	            removeAll();
	            add(baggagePanel);
	            revalidate();
	            repaint();
			}
		});
		backButton.setOpaque(false);
		backButton.setForeground(new Color(131, 131, 131));
		backButton.setFont(new Font("Poppins", Font.PLAIN, 18));
		backButton.setFocusable(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new RoundedBorder(16, Color.BLACK));
		backButton.setBounds(20, 819, 105, 46);
		add(backButton);
		
        JButton nextButton = new JButton("Next");
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("BaggageDatabase.txt"));
					
		            // Iterate through the elements in listModel
		            for (int i = 0; i < listModel.getSize(); i++) {
		                String baggageType = listModel.getElementAt(i);
		                // Trim white spaces and keep only the first string
		                String trimmedBaggageType = baggageType.trim().split("\\|")[0].trim();
		                // Write the trimmed baggage type to the file
		                bw.write(trimmedBaggageType);
		                bw.newLine();
		            }
		            
		            bw.close();
		            
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

            	// Go to next panel (this is just to simulate to extend panels)\
				PageLoader.getInstance().changeToFLightDetails(passengerInfo);
                revalidate();
                repaint();

                clearBaggages();
                updateTotalPriceLabel();
            }
        });
		nextButton.setOpaque(false);
		nextButton.setForeground(new Color(131, 131, 131));
		nextButton.setFont(new Font("Poppins", Font.PLAIN, 18));
		nextButton.setFocusable(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setBorder(new RoundedBorder(16, Color.BLACK));
		nextButton.setBounds(307, 819, 105, 46);
		add(nextButton);
		
		JButton addHandcarry = new JButton("+");
		addHandcarry.setFocusable(false);
		addHandcarry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addBaggageTypeToList(" Handcarry | less than 10kg > 49.00");
				updateTotalPriceLabel();
			}
		});
		addHandcarry.setHorizontalTextPosition(SwingConstants.CENTER);
		addHandcarry.setForeground(Color.WHITE);
		addHandcarry.setBackground(new Color(56,84,147));
		addHandcarry.setFont(new Font("Poppins", Font.PLAIN, 20));
		addHandcarry.setBorder(null);
		addHandcarry.setBounds(25, 225, 25, 25);
		add(addHandcarry);
		
		JButton addStandard = new JButton("+");
		addStandard.setFocusable(false);
		addStandard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addBaggageTypeToList(" Standard | 11kg - 20kg > 69.00");
				updateTotalPriceLabel();
			}
		});
		addStandard.setHorizontalTextPosition(SwingConstants.CENTER);
		addStandard.setForeground(Color.WHITE);
		addStandard.setFont(new Font("Poppins", Font.PLAIN, 20));
		addStandard.setBorder(null);
		addStandard.setBackground(new Color(56, 84, 147));
		addStandard.setBounds(25, 410 + 5, 25, 25);
		add(addStandard);
		
		JButton addHeavy = new JButton("+");
		addHeavy.setFocusable(false);
		addHeavy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addBaggageTypeToList(" Heavy | 21kg - 30kg > 89.00");
				updateTotalPriceLabel();
			}
		});
		addHeavy.setHorizontalTextPosition(SwingConstants.CENTER);
		addHeavy.setForeground(Color.WHITE);
		addHeavy.setFont(new Font("Poppins", Font.PLAIN, 20));
		addHeavy.setBorder(null);
		addHeavy.setBackground(new Color(56, 84, 147));
		addHeavy.setBounds(25, 459 + 5, 25, 25);
		add(addHeavy);
		
		JButton addOversized = new JButton("+");
		addOversized.setFocusable(false);
		addOversized.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addBaggageTypeToList(" Oversized | 31kg+ > 129.00");
				updateTotalPriceLabel();
			}
		});
		addOversized.setHorizontalTextPosition(SwingConstants.CENTER);
		addOversized.setForeground(Color.WHITE);
		addOversized.setFont(new Font("Poppins", Font.PLAIN, 20));
		addOversized.setBorder(null);
		addOversized.setBackground(new Color(56, 84, 147));
		addOversized.setBounds(25, 509 + 5, 25, 25);
		add(addOversized);
		
		JScrollPane scrollPane = new JScrollPane();
        listModel = new DefaultListModel<>();
        
        JButton removeButton = new JButton("Remove");
        removeButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		removeSelectedBaggage();
        		updateTotalPriceLabel();
        	}
        });
        removeButton.setFocusable(false);
        removeButton.setOpaque(false);
        removeButton.setContentAreaFilled(false);
        removeButton.setBounds(323, 555, 89, 25);
        add(removeButton);
        
        totalPriceLabel = new JLabel("Total:");
        totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalPriceLabel.setForeground(new Color(244,129,52));
        totalPriceLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        totalPriceLabel.setBounds(247, 786, 165, 14);
        add(totalPriceLabel);
        
        list = new JList<>(listModel);
        list.setFont(new Font("Poppins", Font.PLAIN, 16));
        list.setForeground(new Color(56,84,147));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        scrollPane.setViewportView(list);
        scrollPane.setBounds(20, 585, 392, 190);
        add(scrollPane);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon("src/Assets/infoImages/BaggageAdd.png"));
		backgroundImage.setBounds(0, 0, 430, 930);
		add(backgroundImage);
		
		updateTotalPriceLabel();
	}
	
	private void clearBaggages() {
	    listModel.clear();
	}
    
	private void updateTotalPriceLabel() {
	    double totalPrice = 0.0;

	    for (int i = 0; i < listModel.getSize(); i++) {
	        String baggageType = listModel.getElementAt(i).trim();
	        String trimmedBaggageType = baggageType.split("\\s*\\|\\s*")[0].trim();
	        
	        if (baggagePrices.containsKey(trimmedBaggageType)) {
	            totalPrice += baggagePrices.get(trimmedBaggageType);
	        }

	    }

	    totalPriceLabel.setText("Total: " + String.format("%.2f", totalPrice) + " PHP");
	}
	
	private void addBaggageTypeToList(String baggageType) {
	    listModel.addElement(baggageType);
	}
    
    private void removeSelectedBaggage() {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
        }
    }
    
	private static class RoundedBorder implements javax.swing.border.Border {
	    private int radius;
	    private Color color;

	    RoundedBorder(int radius, Color color) {
	        this.radius = radius;
	        this.color = color;
	    }

	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
	    }

	    public boolean isBorderOpaque() {
	        return true;
	    }

	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        JButton button = (JButton) c;
	        ButtonModel model = button.getModel();

	        Color currentColor = color;

	        if (model.isPressed() || model.isSelected()) {
	            currentColor = new Color(56, 84, 147);
	        }

	        g.setColor(currentColor);
	        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	    }
	} 
}