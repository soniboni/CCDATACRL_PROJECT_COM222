package InformationSection;

import Abstractions.ImagePanel;
import Abstractions.PageLoader;
import PathDatabase.Path;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FlightDetailsDirect extends ImagePanel {

	private static final long serialVersionUID = 1L;
	private Hashtable<String, String> passengerInfo;
	private JTextField discountField;
	private JPanel content;
	static double total;
	private void addFlights(List<Path> path){
		int flightNumber = 1;
		for (Path p: path) addFlight(flightNumber++, p);
	}

	private void addFlight(int flightNumber, Path p) {
		ImagePanel panel = new ImagePanel("src/Assets/infoImages/flightBackground.png");

		JLabel flightNumberLabel = new JLabel(String.valueOf(flightNumber));
		flightNumberLabel.setFont(new Font("Arial Nova", Font.BOLD, 14));
		flightNumberLabel.setForeground(new Color(38, 44, 78));
		flightNumberLabel.setBounds(50, 0, 142, 18);
		panel.add(flightNumberLabel);

		JLabel pathLabel = new JLabel(p.path.toLowerCase(), SwingConstants.RIGHT);
		pathLabel.setFont(new Font("Arial Nova", Font.PLAIN, 12));
		pathLabel.setForeground(new Color(244, 129, 52));
		pathLabel.setBounds(160, 20, 200, 18);
		panel.add(pathLabel);

		ImagePanel airlineType = new ImagePanel("src/Assets/AirlineLogos/" + p.path.split(" ")[0] + "_airline.png");
		airlineType.setBounds(160, 41, 200, 18);
		airlineType.setBackground(Color.WHITE);
		panel.add(airlineType);

		JLabel type = new JLabel("Economy");
		type.setFont(new Font("Arial Nova", Font.BOLD, 14));
		type.setForeground(new Color(88, 88, 88));
		type.setBounds(11, 81, 200, 18);
		panel.add(type);

		JLabel price = new JLabel(String.format("%,d", p.cost) , SwingConstants.RIGHT);
		price.setFont(new Font("Arial Nova", Font.PLAIN, 13));
		price.setForeground(new Color(244, 129, 52));
		price.setBounds(180, 81, 180, 18);
		panel.add(price);

		content.add(panel);
		System.out.println(content.getHeight());
		content.setSize(new Dimension(410, content.getHeight() + 115));
		content.revalidate();
		this.repaint();
	}

	public FlightDetailsDirect(Hashtable<String, String> passengerInfo) {
		super("src/Assets/infoImages/FlightPage.png");
		setBounds(0, 0, 430, 930);
		setBackground(Color.WHITE);
		content = new JPanel();
		content.setLayout(new FlowLayout(FlowLayout.CENTER));
		content.setBounds(10, 233, 410, 60);
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		content.setBackground(Color.WHITE);

		JPanel header = new JPanel();
		header.setLayout(null);
		header.setSize(new Dimension(410, 60));
		header.setBackground(Color.WHITE);

		JLabel routeLabel = new JLabel("PH > IN > JA".toString().toUpperCase());
		routeLabel.setForeground(new Color(38, 44, 78));
		routeLabel.setFont(new Font("Arial Nova", Font.BOLD, 30));
		routeLabel.setBounds(0, 20, 300, 44);
		header.add(routeLabel);

		JLabel referenceNumber = new JLabel(passengerInfo.get("referenceNumber"), SwingConstants.RIGHT);
		referenceNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		referenceNumber.setForeground(new Color(88, 88, 88));
		referenceNumber.setFont(new Font("Poppins", Font.PLAIN, 12));
		referenceNumber.setBounds(150, 0, 189, 12);
		header.add(referenceNumber);

		content.add(header);
		addFlights(Passenger.getCurrentPath());

		int handQuantity = 0;
		double handcarryPrice = 0.0;
		int checkedQuantity = 0;
		double checkedBaggagePrice = 0.0;

		try {
			BufferedReader br = new BufferedReader(new FileReader("BaggageDatabase.txt"));

			String line = br.readLine();
			while (line != null) {
				switch (line.trim()) {
					case "Handcarry":
						handQuantity++;
						handcarryPrice += getPrice(line.trim());
						break;
					case "Standard":
					case "Heavy":
					case "Oversized":
						checkedQuantity++;
						checkedBaggagePrice += getPrice(line.trim());
						break;
				}
				line = br.readLine();
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImagePanel infoSection = new ImagePanel("src/Assets/infoImages/infoBackground.png");
		infoSection.setSize(410, 400);
		JLabel passengerName = new JLabel(passengerInfo.get("firstName") + " " + passengerInfo.get("lastName"));
		passengerName.setForeground(new Color(244, 129, 5));
		passengerName.setFont(new Font("Poppins", Font.BOLD, 20));
		passengerName.setBounds(11, 27, 336, 23);
		infoSection.add(passengerName);

		JLabel passengerPhoneNumber = new JLabel(passengerInfo.get("phoneNumber"));
		passengerPhoneNumber.setFont(new Font("Poppins", Font.PLAIN, 12));
		passengerPhoneNumber.setBounds(11, 56, 220, 14);
		infoSection.add(passengerPhoneNumber);

		JLabel passengerPassport = new JLabel(passengerInfo.get("passportNumber") + " (" + passengerInfo.get("validityDate") + ")");
		passengerPassport.setFont(new Font("Poppins", Font.PLAIN, 12));
		passengerPassport.setBounds(11, 73, 300, 14);
		infoSection.add(passengerPassport);

		JLabel checkedTotalPrice = new JLabel(String.format("PHP %.2f", checkedBaggagePrice));
		checkedTotalPrice.setForeground(new Color(244,129,52));
		checkedTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		checkedTotalPrice.setFont(new Font("Poppins", Font.PLAIN, 12));
		checkedTotalPrice.setBounds(180, 121, 180, 14);
		infoSection.add(checkedTotalPrice);

		JLabel handcarryTotalPrice = new JLabel(String.format("PHP %.2f", handcarryPrice));
		handcarryTotalPrice.setForeground(new Color(244,129,52));
		handcarryTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		handcarryTotalPrice.setFont(new Font("Poppins", Font.PLAIN, 12));
		handcarryTotalPrice.setBounds(180, 137, 180, 14);
		infoSection.add(handcarryTotalPrice);

		JLabel checkedBaggageQuantity = new JLabel(checkedQuantity + "x Checked Baggage");
		checkedBaggageQuantity.setFont(new Font("Poppins", Font.PLAIN, 12));
		checkedBaggageQuantity.setBounds(10, 121, 189, 14);
		infoSection.add(checkedBaggageQuantity);

		JLabel handcarryQuantity = new JLabel(handQuantity + "x Handcarry");
		handcarryQuantity.setFont(new Font("Poppins", Font.PLAIN, 12));
		handcarryQuantity.setBounds(10, 137, 300, 14);
		infoSection.add(handcarryQuantity);

		JLabel discountPercentage = new JLabel("-0%");
		discountPercentage.setHorizontalAlignment(SwingConstants.RIGHT);
		discountPercentage.setFont(new Font("Poppins", Font.PLAIN, 12));
		discountPercentage.setBounds(180, 247, 180, 14);
		discountPercentage.setForeground(new Color(88,88,88));
		infoSection.add(discountPercentage);

		total = handcarryPrice + checkedBaggagePrice;
		for (Path path: Passenger.getCurrentPath()) total += path.cost;

		JLabel totalPayment = new JLabel(String.format("PHP %,.2f", total));
		totalPayment.setForeground(new Color(244,129,52));
		totalPayment.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPayment.setFont(new Font("Poppins", Font.BOLD, 12));
		totalPayment.setBounds(180, 302, 180, 16);
		infoSection.add(totalPayment);

		discountField = new JTextField();
		discountField.setForeground(new Color(88,88,88));
		discountField.setFont(new Font("Poppins", Font.PLAIN, 16));
		discountField.setColumns(10);
		discountField.setBorder(null);
		discountField.setBounds(10, 186, 209, 30);
		infoSection.add(discountField);

		JButton verifyDiscount = new JButton("Verify");
		verifyDiscount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String enteredDiscountCode = discountField.getText().trim();

				if (isValidDiscountCode(enteredDiscountCode)) {
					verifyDiscount.setVisible(false);
					discountField.setEditable(false);
					total *= 0.10;
					totalPayment.setText(String.format("PHP %,.2f", total));
					discountPercentage.setText("-10%");
				} else {
					discountField.setText(("Invalid Discount Code!"));
				}
			}
		});
		verifyDiscount.setOpaque(false);
		verifyDiscount.setForeground(new Color(131, 131, 131));
		verifyDiscount.setFont(new Font("Poppins", Font.PLAIN, 18));
		verifyDiscount.setFocusable(false);
		verifyDiscount.setContentAreaFilled(false);
		verifyDiscount.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		verifyDiscount.setBounds(267, 194, 105, 32);
		infoSection.add(verifyDiscount);

		content.add(infoSection);
		content.setSize(new Dimension(410, content.getHeight() + infoSection.getHeight()));
		this.add(content);
		this.setPreferredSize(new Dimension(430, 225 + content.getHeight() + 50));
		revalidate();

		ImagePanel footer = new ImagePanel("src/Assets/infoImages/footer.png");
		/*
		JButton backButton = new JButton("Back");
		backButton.setFocusable(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Go back to seat type panel
			}
		});
		backButton.setOpaque(false);
		backButton.setForeground(new Color(131, 131, 131));
		backButton.setFont(new Font("Poppins", Font.PLAIN, 18));
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new RoundedBorder(16, Color.BLACK));
		backButton.setBounds(20, 819, 105, 46);
		add(backButton);

		JButton generateButton = new JButton("Next");
		generateButton.setFocusable(false);
		generateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					BufferedWriter wr = new BufferedWriter(new FileWriter("DiscountDatabase.txt"));

					String discountInput = discountField.getText();
					String percentage = discountPercentage.getText();

					wr.write(discountInput + "|" + percentage);
					wr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                GenerateTicketDirect generateTicket = new GenerateTicketDirect(passengerInfo);

                removeAll();
                add(generateTicket);
                revalidate();
                repaint();
			}
		});
		generateButton.setVisible(false);
		generateButton.setOpaque(false);
		generateButton.setForeground(new Color(131, 131, 131));
		generateButton.setFont(new Font("Poppins", Font.PLAIN, 18));
		generateButton.setContentAreaFilled(false);
		generateButton.setBorder(new RoundedBorder(16, Color.BLACK));
		generateButton.setBounds(302, 819, 105, 46);
		add(generateButton);

		JCheckBox confirmBox = new JCheckBox("");
		confirmBox.addItemListener(e -> {
			// Check if the checkbox is checked
			if (e.getStateChange() == ItemEvent.SELECTED) {
				// Checkbox is checked, make the generateButton visible
				generateButton.setVisible(true);
			} else {
				// Checkbox is unchecked, make the generateButton invisible
				generateButton.setVisible(false);
			}
		});
		confirmBox.setOpaque(false);
		confirmBox.setContentAreaFilled(false);
		confirmBox.setBounds(60, 759, 29, 23);
		add(confirmBox);

		JLabel currentDestination = new JLabel("PH");
		currentDestination.setForeground(new Color(72,98,159));
		currentDestination.setFont(new Font("Poppins", Font.BOLD, 30));
		currentDestination.setBounds(37, 253, 46, 30);
		add(currentDestination);

		JLabel nextDestination = new JLabel("N/A");
		nextDestination.setForeground(new Color(72, 98, 159));
		nextDestination.setFont(new Font("Poppins", Font.BOLD, 30));
		nextDestination.setBounds(105, 253, 58, 30);
		add(nextDestination);

		JLabel airlinePlaceholder = new JLabel("Philippine Airlines");
		airlinePlaceholder.setHorizontalAlignment(SwingConstants.RIGHT);
		airlinePlaceholder.setForeground(new Color(88, 88, 88));
		airlinePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		airlinePlaceholder.setBounds(196, 311, 189, 14);
		add(airlinePlaceholder);

		JLabel seatTypePlaceholder = new JLabel("Placeholder");
		seatTypePlaceholder.setHorizontalAlignment(SwingConstants.LEFT);
		seatTypePlaceholder.setForeground(new Color(88, 88, 88));
		seatTypePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		seatTypePlaceholder.setBounds(37, 368, 137, 14);
		add(seatTypePlaceholder);

		JLabel seatTypePricePlaceholder = new JLabel("PHP 0.00");
		seatTypePricePlaceholder.setHorizontalAlignment(SwingConstants.RIGHT);
		seatTypePricePlaceholder.setForeground(new Color(244, 129, 52));
		seatTypePricePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		seatTypePricePlaceholder.setBounds(248, 368, 137, 14);
		add(seatTypePricePlaceholder);

		 */
	}
	
    private boolean isValidDiscountCode(String discountCode) {
        // Check if the code starts with STU, SEN, or PWD
        if (discountCode.startsWith("STU") || discountCode.startsWith("SEN") || discountCode.startsWith("PWD")) {
            // Check if the total length is not more than 9 characters
            if (discountCode.length() == 9) {
                return true; // Valid discount code
            }
        }
        return false; // Invalid discount code
    }
	
	private double getPrice(String baggageType) {
		switch (baggageType) {
		case "Handcarry":
			return 49.00;
		case "Standard":
			return 69.00;
		case "Heavy":
			return 89.00;
		case "Oversized":
			return 129.00;
		default:
			return 0.0;
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
