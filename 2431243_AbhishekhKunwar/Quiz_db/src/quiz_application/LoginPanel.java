package quiz_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Constructor to set up the UI
    public LoginPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        JPanel startPanel = createStartPanel();
        JPanel registerLoginPanel = createRegisterLoginPanel();
        JPanel rulesPanel = createRulesPanel();

        cardPanel.add(startPanel, "startPanel");
        cardPanel.add(registerLoginPanel, "registerLoginPanel");
        cardPanel.add(rulesPanel, "rulesPanel");

        add(cardPanel);
    }

    // Start panel where user chooses to either log in as admin or register/login as
    // user
    private JPanel createStartPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(StyleConstants.BACKGROUND_COLOR);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(StyleConstants.CARD_BACKGROUND);
        card.setBorder(StyleConstants.CARD_BORDER);

        JLabel titleLabel = new JLabel("Welcome to Quiz Pro", JLabel.CENTER);
        titleLabel.setFont(StyleConstants.TITLE_FONT);
        titleLabel.setForeground(StyleConstants.PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Join the elite league of thinkers", JLabel.CENTER);
        subLabel.setFont(StyleConstants.BODY_FONT);
        subLabel.setForeground(StyleConstants.TEXT_SECONDARY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton adminButton = new JButton("Admin Portal");
        StyleConstants.styleSecondaryButton(adminButton);
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminButton.addActionListener(e -> {
            String adminUsername = JOptionPane.showInputDialog(this, "Enter admin username:");
            String adminPassword = JOptionPane.showInputDialog(this, "Enter admin password:");

            if ("admin".equals(adminUsername) && "admin123".equals(adminPassword)) {
                MainFrame.getInstance().showAdmin();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials!");
            }
        });

        JButton userButton = new JButton("Get Started");
        StyleConstants.styleButton(userButton);
        userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userButton.addActionListener(e -> cardLayout.show(cardPanel, "registerLoginPanel"));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(subLabel);
        card.add(Box.createVerticalStrut(40));
        card.add(userButton);
        card.add(Box.createVerticalStrut(15));
        card.add(adminButton);

        container.add(card);
        return container;
    }

    // Register and Login panel to enter name and age
    private JPanel createRegisterLoginPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(StyleConstants.BACKGROUND_COLOR);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(StyleConstants.CARD_BACKGROUND);
        card.setBorder(StyleConstants.CARD_BORDER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Account Access", JLabel.CENTER);
        titleLabel.setFont(StyleConstants.SUBTITLE_FONT);
        titleLabel.setForeground(StyleConstants.PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(StyleConstants.BODY_FONT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        card.add(nameLabel, gbc);

        JTextField nameField = new JTextField();
        styleTextField(nameField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        card.add(nameField, gbc);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(StyleConstants.BODY_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(ageLabel, gbc);

        JTextField ageField = new JTextField();
        styleTextField(ageField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        card.add(ageField, gbc);

        JButton registerButton = new JButton("Create Account");
        StyleConstants.styleSecondaryButton(registerButton);
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            int age;
            try {
                age = Integer.parseInt(ageField.getText());
                if (name.isEmpty() || age <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid details.");
                } else {
                    registerUser(name, age);
                    JOptionPane.showMessageDialog(this, "Registration Successful! You can now Login.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid age entered.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        card.add(registerButton, gbc);

        JButton loginButton = new JButton("Login to Play");
        StyleConstants.styleButton(loginButton);
        loginButton.addActionListener(e -> {
            String enteredName = nameField.getText();
            User user = findUserByName(enteredName);
            if (user != null) {
                startQuiz(user);
            } else {
                JOptionPane.showMessageDialog(card, "User not found. Please register first.");
            }
        });

        gbc.gridy = 4;
        card.add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setFont(StyleConstants.BODY_FONT);
        backButton.setForeground(StyleConstants.DANGER_COLOR);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "startPanel"));
        gbc.gridy = 5;
        card.add(backButton, gbc);

        container.add(card);
        return container;
    }

    // Rules Panel
    private JPanel createRulesPanel() {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(StyleConstants.BACKGROUND_COLOR);

        JPanel card = new JPanel(new BorderLayout(15, 15));
        card.setBackground(StyleConstants.CARD_BACKGROUND);
        card.setBorder(StyleConstants.CARD_BORDER);

        JLabel rulesTitle = new JLabel("Official Guidelines", JLabel.CENTER);
        rulesTitle.setFont(StyleConstants.SUBTITLE_FONT);
        rulesTitle.setForeground(StyleConstants.PRIMARY_COLOR);
        card.add(rulesTitle, BorderLayout.NORTH);

        JTextArea rulesArea = new JTextArea(
                "• All questions are compulsory - no skipping!\n" +
                        "• Each question must be answered within 30 seconds.\n" +
                        "• Points are awarded for each correct answer based on level.\n" +
                        "• Your progress is saved automatically.\n" +
                        "• Maintain sportsmanship and enjoy the challenge.");
        rulesArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        rulesArea.setForeground(StyleConstants.TEXT_PRIMARY);
        rulesArea.setEditable(false);
        rulesArea.setLineWrap(true);
        rulesArea.setWrapStyleWord(true);
        rulesArea.setOpaque(false);

        JScrollPane scroll = new JScrollPane(rulesArea);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        card.add(scroll, BorderLayout.CENTER);

        JButton startButton = new JButton("I am Ready!");
        StyleConstants.styleButton(startButton);
        startButton.addActionListener(e -> {
            startQuiz(null); // Assuming user is cached or handled
        });
        card.add(startButton, BorderLayout.SOUTH);

        container.add(card);
        return container;
    }

    // Save user data to the database
    private void registerUser(String name, int age) {
        String query = "INSERT INTO users (name, age) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.executeUpdate();
            System.out.println("User registered successfully!");
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    // Fetch user data from the database
    private User findUserByName(String name) {
        String query = "SELECT * FROM users WHERE name = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                return new User(id, name, age, 0, null);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }
        return null; // Return null if user is not found
    }

    // Start the quiz with the logged-in user
    private void startQuiz(User user) {
        String level = JOptionPane.showInputDialog("Enter quiz level (Beginner, Intermediate, Advanced):");
        if (level != null && !level.isEmpty()) {
            // Save the level played by the user
            updateUserScoreAndLevel(user, 0, level); // Initialize score to 0
            MainFrame.getInstance().showQuiz(user, level);
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid quiz level.");
        }
    }

    // Method to update user's score and level in the database
    private void updateUserScoreAndLevel(User user, int score, String level) {
        String query = "UPDATE users SET score = ?, level = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, score);
            preparedStatement.setString(2, level);
            preparedStatement.setInt(3, user.getId());
            preparedStatement.executeUpdate();
            System.out.println("User score and level updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating user score and level: " + e.getMessage());
        }
    }

    // Utility function to style buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(60, 120, 255)); // Blue color for button
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Utility function to style labels
    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(70, 70, 70));
    }

    // Utility function to style text fields
    private void styleTextField(JTextField textField) {
        textField.setFont(StyleConstants.BODY_FONT);
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    // Main method removed as MainFrame is now the entry point
}