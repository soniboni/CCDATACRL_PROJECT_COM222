package InformationSection;

import Abstractions.PageLoader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerDetails extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phoneNumber;
    private JTextField emailAddress;
    private JTextField passportNumber;
    private JTextField validityDate;
    private JLabel backgroundImage;
    private JLabel errorMessage;
    private JButton backButton;

    public PassengerDetails() {
        setBounds(0, 0, 430, 930);
        setLayout(null);
        setBackground(Color.WHITE);

        lastName = new JTextField();
        lastName.setText("Juan");
        lastName.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        lastName.setColumns(10);
        lastName.setBorder(null);
        lastName.setBounds(35, 357, 360, 30);
        add(lastName);

        firstName = new JTextField();
        firstName.setText("Dela Cruz");
        firstName.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        firstName.setBorder(null);
        firstName.setBounds(35, 288, 360, 30);
        add(firstName);
        firstName.setColumns(10);

        phoneNumber = new JTextField();
        phoneNumber.setText("09695602456");
        phoneNumber.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        phoneNumber.setColumns(10);
        phoneNumber.setBorder(null);
        phoneNumber.setBounds(35, 467, 360, 30);
        add(phoneNumber);

        emailAddress = new JTextField();
        emailAddress.setText("juandelacruz@gmail.com");
        emailAddress.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        emailAddress.setColumns(10);
        emailAddress.setBorder(null);
        emailAddress.setBounds(35, 536, 360, 30);
        add(emailAddress);

        passportNumber = new JTextField();
        passportNumber.setText("PA1234567");
        passportNumber.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        passportNumber.setColumns(10);
        passportNumber.setBorder(null);
        passportNumber.setBounds(35, 648, 360, 30);
        add(passportNumber);

        validityDate = new JTextField();
        validityDate.setText("01/02/2024");
        validityDate.setFont(new Font("Arial Nova", Font.PLAIN, 12));
        validityDate.setColumns(10);
        validityDate.setBorder(null);
        validityDate.setBounds(35, 717, 360, 30);
        add(validityDate);

        backButton = new JButton("Back");
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Go back function for before the page of this page
            }
        });
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setBorder(new RoundedBorder(16, Color.BLACK)); // Set the border color to black
        backButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        backButton.setForeground(new Color(131, 131, 131));
        backButton.setBounds(20, 819, 105, 46);
        add(backButton);

        JButton nextButton = new JButton("Next");
        nextButton.setRequestFocusEnabled(false);
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                String passengerFirstName = firstName.getText();
                String passengerLastName = lastName.getText();
                String passengerPhoneNumber = phoneNumber.getText();
                String passengerEmail = emailAddress.getText();
                String passengerPassport = passportNumber.getText();
                String passengerValidity = validityDate.getText();

                if (passengerFirstName.isEmpty() || passengerLastName.isEmpty() || passengerPhoneNumber.isEmpty()
                        || passengerEmail.isEmpty() || passengerPassport.isEmpty() || passengerValidity.isEmpty()) {
                    errorMessage.setText("Fill out all required fields before continuing.");
                } else if (!passengerPhoneNumber.matches("\\d+") || passengerPhoneNumber.length() != 11) {
                    errorMessage.setText("Enter a valid phone number.");
                } else if (!isValidEmail(passengerEmail)) {
                    errorMessage.setText("Enter a valid email address");
                } else if (!passengerPassport.matches("PA\\d{7}")) {
                    errorMessage.setText("Enter a valid passport number.");
                } else if (!isValidDate(passengerValidity)) {
                    errorMessage.setText("Enter a valid validity date.");
                } else {
                    Passenger.addPassenger(passengerFirstName, passengerLastName, passengerPhoneNumber,
                            passengerEmail, passengerPassport, passengerValidity);
                    PageLoader.getInstance().changeToBaggagePage();
    	            revalidate();
    	            repaint();
                }
            }
        });

        nextButton.setOpaque(false);
        nextButton.setForeground(new Color(131, 131, 131));
        nextButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        nextButton.setContentAreaFilled(false);
        nextButton.setBorder(new RoundedBorder(16, Color.BLACK));
        nextButton.setBounds(305, 819, 105, 46);
        add(nextButton);

        errorMessage = new JLabel("");
        errorMessage.setFont(new Font("Poppins", Font.PLAIN, 12));
        errorMessage.setForeground(new Color(197, 26, 26));
        errorMessage.setBounds(20, 770, 390, 20);
        add(errorMessage);

        backgroundImage = new JLabel("");
        backgroundImage.setIcon(new ImageIcon("src/Assets/infoImages/PassengerPage.png"));
        backgroundImage.setFont(new Font("Poppins", Font.PLAIN, 14));
        backgroundImage.setOpaque(true);
        backgroundImage.setBounds(0, 0, 430, 930);
        add(backgroundImage);
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
            g.setColor(color);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false); // Disable lenient parsing
        try {
            Date validityDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(validityDate);
            int year = calendar.get(Calendar.YEAR);
            return year > 2023;
        } catch (ParseException e) {
            return false; // Invalid date
        }
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a simple email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+@(gmail\\.com|yahoo\\.com\\hotmail\\.com|outlook\\.com)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}