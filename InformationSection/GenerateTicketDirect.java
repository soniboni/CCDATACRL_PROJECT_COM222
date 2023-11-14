package InformationSection;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GenerateTicketDirect extends JPanel {

	private static final long serialVersionUID = 1L;
	private Hashtable<String, String> passengerInfo;
	
	JLabel discountCode;
	JLabel discountPercentage;
	
	public GenerateTicketDirect(Hashtable<String, String> passengerInfo) {

		this.passengerInfo = passengerInfo;

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
		
		
		setBounds(0, 0, 430, 930);
		setLayout(null);

		JLabel passengerName = new JLabel(passengerInfo.get("firstName") + " " + passengerInfo.get("lastName"));
		passengerName.setForeground(new Color(244, 129, 5));
		passengerName.setFont(new Font("Poppins", Font.BOLD, 20));
		passengerName.setBounds(47, 451, 336, 23);
		add(passengerName);

		JLabel passengerPhoneNumber = new JLabel(passengerInfo.get("phoneNumber"));
		passengerPhoneNumber.setFont(new Font("Poppins", Font.PLAIN, 12));
		passengerPhoneNumber.setBounds(47, 475, 220, 14);
		add(passengerPhoneNumber);

		JLabel passengerPassport = new JLabel(
				passengerInfo.get("passportNumber") + " (" + passengerInfo.get("validityDate") + ")");
		passengerPassport.setFont(new Font("Poppins", Font.PLAIN, 12));
		passengerPassport.setBounds(47, 492, 300, 14);
		add(passengerPassport);

		JLabel handcarryQuantity = new JLabel(handQuantity + "x Handcarry");
		handcarryQuantity.setFont(new Font("Poppins", Font.PLAIN, 12));
		handcarryQuantity.setBounds(47, 539, 300, 14);
		add(handcarryQuantity);

		JLabel checkedBaggageQuantity = new JLabel(checkedQuantity + "x Checked Baggage");
		checkedBaggageQuantity.setFont(new Font("Poppins", Font.PLAIN, 12));
		checkedBaggageQuantity.setBounds(47, 558, 189, 14);
		add(checkedBaggageQuantity);

		JLabel checkedTotalPrice = new JLabel(String.format("PHP %.2f", checkedBaggagePrice));
		checkedTotalPrice.setForeground(new Color(244, 129, 52));
		checkedTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		checkedTotalPrice.setFont(new Font("Poppins", Font.PLAIN, 12));
		checkedTotalPrice.setBounds(246, 558, 137, 14);
		add(checkedTotalPrice);

		JLabel handcarryTotalPrice = new JLabel(String.format("PHP %.2f", handcarryPrice));
		handcarryTotalPrice.setForeground(new Color(244, 129, 52));
		handcarryTotalPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		handcarryTotalPrice.setFont(new Font("Poppins", Font.PLAIN, 12));
		handcarryTotalPrice.setBounds(246, 539, 137, 14);
		add(handcarryTotalPrice);

		JLabel referenceNumber = new JLabel(passengerInfo.get("referenceNumber"));
		referenceNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		referenceNumber.setFont(new Font("Poppins", Font.PLAIN, 12));
		referenceNumber.setForeground(new Color(88, 88, 88));
		referenceNumber.setBounds(194, 259, 189, 14);
		add(referenceNumber);

		JLabel totalPayment = new JLabel(" PHP 0.00");
		totalPayment.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPayment.setForeground(new Color(244, 129, 52));
		totalPayment.setFont(new Font("Poppins", Font.BOLD, 12));
		totalPayment.setBounds(246, 639, 137, 14);
		add(totalPayment);

		JLabel airlinePlaceholder = new JLabel("Philippine Airlines");
		airlinePlaceholder.setHorizontalAlignment(SwingConstants.RIGHT);
		airlinePlaceholder.setForeground(new Color(88, 88, 88));
		airlinePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		airlinePlaceholder.setBounds(194, 335, 189, 14);
		add(airlinePlaceholder);

		JLabel seatTypePlaceholder = new JLabel("Placeholder");
		seatTypePlaceholder.setHorizontalAlignment(SwingConstants.LEFT);
		seatTypePlaceholder.setForeground(new Color(88, 88, 88));
		seatTypePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		seatTypePlaceholder.setBounds(47, 391, 137, 14);
		add(seatTypePlaceholder);

		JLabel seatTypePricePlaceholder = new JLabel("PHP 0.00");
		seatTypePricePlaceholder.setHorizontalAlignment(SwingConstants.RIGHT);
		seatTypePricePlaceholder.setForeground(new Color(244, 129, 52));
		seatTypePricePlaceholder.setFont(new Font("Poppins", Font.PLAIN, 12));
		seatTypePricePlaceholder.setBounds(246, 391, 137, 14);
		add(seatTypePricePlaceholder);

		discountPercentage = new JLabel();
		discountPercentage.setHorizontalAlignment(SwingConstants.RIGHT);
		discountPercentage.setForeground(new Color(88, 88, 88));
		discountPercentage.setFont(new Font("Poppins", Font.PLAIN, 12));
		discountPercentage.setBounds(351, 586, 32, 14);
		add(discountPercentage);

		discountCode = new JLabel();
		discountCode.setHorizontalAlignment(SwingConstants.LEFT);
		discountCode.setForeground(new Color(88, 88, 88));
		discountCode.setFont(new Font("Poppins", Font.PLAIN, 12));
		discountCode.setBounds(271, 586, 74, 14);
		add(discountCode);

		JButton downloadButton = new JButton("DOWNLOAD");
		downloadButton.setBorder(null);
		downloadButton.setRequestFocusEnabled(false);
		downloadButton.setFocusable(false);
		downloadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				captureAndSavePanelImage();
				JOptionPane.showMessageDialog(null, "Ticket receipt successfully downloaded!", "GABAY Administrator",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		downloadButton.setForeground(Color.WHITE);
		downloadButton.setOpaque(false);
		downloadButton.setContentAreaFilled(false);
		downloadButton.setFont(new Font("Poppins", Font.BOLD, 13));
		downloadButton.setBounds(69, 762, 122, 23);
		add(downloadButton);

		JButton shareButton = new JButton("SHARE");
		shareButton.setBorder(null);
		shareButton.setRequestFocusEnabled(false);
		shareButton.setFocusable(false);
		shareButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Share is in development!");
			}
		});
		shareButton.setOpaque(false);
		shareButton.setForeground(Color.WHITE);
		shareButton.setFont(new Font("Poppins", Font.BOLD, 13));
		shareButton.setContentAreaFilled(false);
		shareButton.setBounds(271, 762, 122, 23);
		add(shareButton);
		
		JLabel currentDestination = new JLabel("PH");
		currentDestination.setForeground(new Color(72, 98, 159));
		currentDestination.setFont(new Font("Poppins", Font.BOLD, 30));
		currentDestination.setBounds(47, 275, 46, 30);
		add(currentDestination);
		
		JButton backButton = new JButton("Done");
		backButton.setFocusable(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Start again in the beginning
			}
		});
		backButton.setOpaque(false);
		backButton.setForeground(Color.WHITE);
		backButton.setFont(new Font("Poppins", Font.PLAIN, 18));
		backButton.setContentAreaFilled(false);
		backButton.setBorder(new RoundedBorder(16, Color.WHITE));
		backButton.setBounds(302, 819, 105, 46);
		add(backButton);
		
		JLabel nextDestination = new JLabel("N/A");
		nextDestination.setForeground(new Color(72, 98, 159));
		nextDestination.setFont(new Font("Poppins", Font.BOLD, 30));
		nextDestination.setBounds(117, 275, 58, 30);
		add(nextDestination);
		
				JLabel backgroundImage = new JLabel("");
				backgroundImage.setIcon(new ImageIcon("src/Assets/infoImages/GenerateTicketDirectPage.png"));
				backgroundImage.setBounds(0, 0, 430, 930);
				add(backgroundImage);
		
		readDiscount();
	}

	private void readDiscount() {
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("DiscountDatabase.txt"));
	        String line = reader.readLine();

	        if (line != null) {
	            // Split the line into discount code and percentage
	            String[] parts = line.split("\\|");

	            // Update the labels with the retrieved information
	            discountCode.setText(parts[0]);
	            discountPercentage.setText(parts[1]);
	        }

	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void captureAndSavePanelImage() {
		try {
			// Create a BufferedImage with the same size as the panel
			BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();

			// Paint the panel to the BufferedImage
			paint(g);

			// Dispose of the graphics context to release resources
			g.dispose();

			// Save the BufferedImage to a file
			File outputFile = new File("Ticket Receipt.png");
			ImageIO.write(image, "png", outputFile);

			System.out.println("Image saved successfully!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
