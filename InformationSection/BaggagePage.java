package InformationSection;

import Abstractions.PageLoader;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class BaggagePage extends JPanel {

    private static final long serialVersionUID = 1L;
    JLabel infoLabel;
    JButton nextButton;
    private final JButton noBaggageButton;
    private final JButton addBaggageButton;

    public BaggagePage() {
        setBounds(0, 0, 430, 930);
        setLayout(null);
        setBackground(Color.WHITE);

        noBaggageButton = createButton("No Baggage");
        noBaggageButton.setBounds(20, 232, 390, 60);
        add(noBaggageButton);

        addBaggageButton = createButton("Add Baggage");
        addBaggageButton.setBounds(20, 311, 390, 60);
        add(addBaggageButton);

        JButton backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setOpaque(false);
        backButton.setForeground(new Color(131, 131, 131));
        backButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        backButton.setContentAreaFilled(false);
        backButton.setBorder(new RoundedBorder(16, Color.BLACK));
        backButton.setBounds(20, 819, 105, 46);
        add(backButton);

        nextButton = new JButton("Next");
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("BaggageDatabase.txt"));

                    bw.write("No Baggage");
                    bw.newLine();
                    bw.close();

                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                Hashtable<String, String> currentUserInfo = Passenger.getCurrentUserInfo();
                PageLoader.getInstance().changeToFLightDetails(currentUserInfo);
                revalidate();
                repaint();
            }
        });
        nextButton.setFocusable(false);
        nextButton.setOpaque(false);
        nextButton.setForeground(new Color(131, 131, 131));
        nextButton.setFont(new Font("Poppins", Font.PLAIN, 18));
        nextButton.setContentAreaFilled(false);
        nextButton.setBorder(new RoundedBorder(16, Color.BLACK));
        nextButton.setBounds(307, 819, 105, 46);
        nextButton.setVisible(false);
        add(nextButton);

        infoLabel = new JLabel("");
        infoLabel.setForeground(new Color(56, 84, 147));
		infoLabel.setFont(new Font("Poppins", Font.ITALIC, 12));
        infoLabel.setBounds(20, 384, 148, 14);
        add(infoLabel);

        JLabel backgroundImage = new JLabel("");
        backgroundImage.setIcon(new ImageIcon("src/Assets/infoImages/BaggagePage.png"));
        backgroundImage.setBounds(0, 0, 430, 930);
        add(backgroundImage);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(Color.WHITE);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (button == noBaggageButton) {
                    handleButtonSelection(noBaggageButton, addBaggageButton);
                    if (noBaggageButton.isSelected()) {
                        infoLabel.setText("No Baggage Selected!");
                        nextButton.setVisible(true);
                    } else {
                        infoLabel.setText("");
                        nextButton.setVisible(false);
                    }
                } else if (button == addBaggageButton) {
                    handleButtonSelection(addBaggageButton, noBaggageButton);
                    infoLabel.setText("");
                    nextButton.setVisible(false);

                    Hashtable<String, String> currentUserInfo = Passenger.getCurrentUserInfo();
                    PageLoader.getInstance().changeToBaggageAdd(currentUserInfo);
                    revalidate();
                    repaint();

                } else {
                    infoLabel.setText("");
                    nextButton.setVisible(false);
                }
            }
        });

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(model.isPressed() || model.isSelected() ? new Color(56, 84, 147) : b.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 16, 16);
                super.paint(g, c);
            }
        });

        button.setForeground(new Color(56, 84, 147));
        button.setFont(new Font("Arial Nova", Font.BOLD, 22));
        button.setBorder(new RoundedBorder(16, new Color(56, 84, 147)));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        return button;
    }

    private void handleButtonSelection(JButton selectedButton, JButton otherButton) {
        boolean isSelected = !selectedButton.isSelected();

        selectedButton.setBackground(isSelected ? new Color(56, 84, 147) : Color.WHITE);
        selectedButton.setForeground(isSelected ? Color.WHITE : new Color(56, 84, 147));
        selectedButton.setBorder(new RoundedBorder(16, new Color(56, 84, 147)));
        selectedButton.setSelected(isSelected);

        otherButton.setBackground(Color.WHITE);
        otherButton.setForeground(new Color(56, 84, 147));
        otherButton.setBorder(new RoundedBorder(16, new Color(56, 84, 147)));
        otherButton.setSelected(false);
    }

    private static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        private final Color color;

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
