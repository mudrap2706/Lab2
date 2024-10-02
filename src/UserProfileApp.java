/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mudra
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class UserProfileApp {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField emailField;
    private JLabel photoLabel;
    private ImageIcon userImage;

    public UserProfileApp() {
        // Create the main frame
        JFrame frame = new JFrame("User Profile Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initialize input fields with background color
        firstNameField = createTextField();
        lastNameField = createTextField();
        ageField = createTextField();
        phoneField = createTextField();
        emailField = createTextField();
        photoLabel = new JLabel("No photo selected");
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // buttons for uploading photo and submitting
        JButton uploadButton = createStyledButton("Upload Photo", new Color(70, 130, 180)); // Steel Blue
        JButton submitButton = createStyledButton("Submit", new Color(34, 139, 34)); // Forest Green

        // Add components to frame
        addComponentToFrame(frame, gbc, 0, 0, new JLabel("First Name:"), firstNameField);
        addComponentToFrame(frame, gbc, 0, 1, new JLabel("Last Name:"), lastNameField);
        addComponentToFrame(frame, gbc, 0, 2, new JLabel("Age:"), ageField);
        addComponentToFrame(frame, gbc, 0, 3, new JLabel("Phone Number:"), phoneField);
        addComponentToFrame(frame, gbc, 0, 4, new JLabel("Email:"), emailField);
        addComponentToFrame(frame, gbc, 0, 5, uploadButton, photoLabel);
        addComponentToFrame(frame, gbc, 0, 6, submitButton);

        // Action listeners for buttons
        uploadButton.addActionListener(e -> uploadPhoto());
        submitButton.addActionListener(e -> submitProfile());

        frame.getContentPane().setBackground(new Color(240, 240, 240)); // Light Gray
        frame.setVisible(true);
    }

    // basic text field with a light background
    private JTextField createTextField() {
        JTextField textField = new JTextField(15);
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBackground(new Color(255, 255, 255)); // White background for text fields
        textField.setForeground(Color.BLACK); // Black text
        return textField;
    }

    // Create a styled button
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLUE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Added padding
        return button;
    }

    // Method to add components to the frame with specific grid positions
    private void addComponentToFrame(JFrame frame, GridBagConstraints gbc, int x, int y, Component... components) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = components.length; // Allow for multiple components in a row
        for (Component comp : components) {
            frame.add(comp, gbc);
            gbc.gridx++; // Move to the next column
        }
    }

    private void uploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                // Scale the image for display
                Image scaledImage = img.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
                userImage = new ImageIcon(scaledImage);
                photoLabel.setIcon(userImage);
                photoLabel.setText(selectedFile.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please upload image correctly.", "Error - Incorrect Image", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void submitProfile() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (validateFields(firstName, lastName, age, phone, email)) {
            String message = String.format("Profile Created:\nName: %s %s\nAge: %s\nPhone: %s\nEmail: %s\nPhoto: %s",
                    firstName, lastName, age, phone, email, userImage != null ? photoLabel.getText() : "No photo");
            JOptionPane.showMessageDialog(null, message, "Profile Summary", JOptionPane.INFORMATION_MESSAGE, userImage);
        }
    }

    private boolean validateFields(String firstName, String lastName, String age, String phone, String email) {
        if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            int ageValue = Integer.parseInt(age);
            if (ageValue < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Age must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserProfileApp::new);
    }
}
