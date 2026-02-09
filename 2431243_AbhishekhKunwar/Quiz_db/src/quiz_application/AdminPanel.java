package quiz_application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JPanel {
    private JButton addQuestionButton, viewQuestionsButton, updateQuestionButton, deleteQuestionButton,
            viewReportsButton, logoutButton;
    private JTextArea questionTextArea;

    public AdminPanel() {
        setOpaque(false);
        setLayout(new BorderLayout(0, 0));

        initComponents();
    }

    private void initComponents() {
        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(StyleConstants.PRIMARY_COLOR);
        sidebar.setPreferredSize(new Dimension(250, 750));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel logoLabel = new JLabel("QUIZ PRO", JLabel.CENTER);
        logoLabel.setFont(StyleConstants.TITLE_FONT);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel adminLabel = new JLabel("Console", JLabel.CENTER);
        adminLabel.setFont(StyleConstants.BODY_FONT);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(logoLabel);
        sidebar.add(adminLabel);
        sidebar.add(Box.createVerticalStrut(40));

        addAdminButton(sidebar, "Add Question", e -> openAddQuestionDialog());
        addAdminButton(sidebar, "View Database", e -> viewQuestions());
        addAdminButton(sidebar, "Analytics", e -> viewReports());

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = new JButton("Logout");
        StyleConstants.styleSecondaryButton(logoutBtn);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> logout());
        sidebar.add(logoutBtn);

        // Main Content
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setBackground(StyleConstants.BACKGROUND_COLOR);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel welcomeLabel = new JLabel("System Overview");
        welcomeLabel.setFont(StyleConstants.TITLE_FONT);
        welcomeLabel.setForeground(StyleConstants.TEXT_PRIMARY);
        mainContent.add(welcomeLabel, BorderLayout.NORTH);

        questionTextArea = new JTextArea();
        questionTextArea.setEditable(false);
        questionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        questionTextArea.setForeground(StyleConstants.TEXT_PRIMARY);
        questionTextArea.setBackground(Color.WHITE);
        questionTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        scrollPane.setBorder(null);
        mainContent.add(scrollPane, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    private void addAdminButton(JPanel parent, String text, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(210, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(StyleConstants.BUTTON_FONT);
        btn.setBackground(Color.WHITE);
        btn.setForeground(StyleConstants.PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(StyleConstants.HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });
        
        parent.add(btn);
        parent.add(Box.createVerticalStrut(15));
    }

    private void openAddQuestionDialog() {
        JDialog addDialog = new JDialog(MainFrame.getInstance(), "Add New Question", true);
        addDialog.setSize(900, 700);
        addDialog.setLocationRelativeTo(MainFrame.getInstance());
        addDialog.setLayout(new BorderLayout(20, 20));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(StyleConstants.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        JLabel headerLabel = new JLabel("Add New Question");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        addDialog.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 10, 12, 10);
        
        // Question field
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        JLabel questionLabel = new JLabel("Question:");
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionLabel.setForeground(Color.BLACK);
        formPanel.add(questionLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextArea questionField = new JTextArea(3, 40);
        questionField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionField.setLineWrap(true);
        questionField.setWrapStyleWord(true);
        questionField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane questionScroll = new JScrollPane(questionField);
        formPanel.add(questionScroll, gbc);
        
        // Option A
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel optionALabel = new JLabel("Option A:");
        optionALabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        optionALabel.setForeground(Color.BLACK);
        formPanel.add(optionALabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField optionAField = new JTextField(40);
        StyleConstants.styleTextField(optionAField);
        formPanel.add(optionAField, gbc);
        
        // Option B
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel optionBLabel = new JLabel("Option B:");
        optionBLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        optionBLabel.setForeground(Color.BLACK);
        formPanel.add(optionBLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField optionBField = new JTextField(40);
        StyleConstants.styleTextField(optionBField);
        formPanel.add(optionBField, gbc);
        
        // Option C
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        JLabel optionCLabel = new JLabel("Option C:");
        optionCLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        optionCLabel.setForeground(Color.BLACK);
        formPanel.add(optionCLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField optionCField = new JTextField(40);
        StyleConstants.styleTextField(optionCField);
        formPanel.add(optionCField, gbc);
        
        // Option D
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        JLabel optionDLabel = new JLabel("Option D:");
        optionDLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        optionDLabel.setForeground(Color.BLACK);
        formPanel.add(optionDLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        JTextField optionDField = new JTextField(40);
        StyleConstants.styleTextField(optionDField);
        formPanel.add(optionDField, gbc);
        
        // Correct Answer
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        JLabel correctLabel = new JLabel("Correct Answer:");
        correctLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        correctLabel.setForeground(Color.BLACK);
        formPanel.add(correctLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 1;
        JComboBox<String> correctAnswerCombo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        correctAnswerCombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        correctAnswerCombo.setPreferredSize(new Dimension(100, 50));
        formPanel.add(correctAnswerCombo, gbc);
        
        // Level
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        JLabel levelLabel = new JLabel("Level:");
        levelLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        levelLabel.setForeground(Color.BLACK);
        formPanel.add(levelLabel, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 1;
        JComboBox<String> levelCombo = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelCombo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        levelCombo.setPreferredSize(new Dimension(200, 50));
        formPanel.add(levelCombo, gbc);
        
        addDialog.add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton cancelButton = new CustomButton("Cancel", Color.WHITE, Color.BLACK, StyleConstants.HOVER_COLOR);
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)));
        cancelButton.addActionListener(e -> addDialog.dispose());
        
        JButton saveButton = new CustomButton("Save Question", StyleConstants.PRIMARY_COLOR, Color.WHITE, StyleConstants.PRIMARY_DARK);
        saveButton.addActionListener(e -> {
            String question = questionField.getText().trim();
            String optionA = optionAField.getText().trim();
            String optionB = optionBField.getText().trim();
            String optionC = optionCField.getText().trim();
            String optionD = optionDField.getText().trim();
            String correctAnswer = correctAnswerCombo.getSelectedItem().toString().toLowerCase();
            String level = levelCombo.getSelectedItem().toString();
            
            if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || 
                optionC.isEmpty() || optionD.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            addQuestionToDatabase(question, optionA, optionB, optionC, optionD, correctAnswer, level);
            addDialog.dispose();
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        addDialog.setVisible(true);
    }

    private void addQuestionToDatabase(String question, String optionA, String optionB, String optionC, String optionD,
            String correctAnswer, String level) {
        String query = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer, level) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, optionA);
            preparedStatement.setString(3, optionB);
            preparedStatement.setString(4, optionC);
            preparedStatement.setString(5, optionD);
            preparedStatement.setString(6, correctAnswer);
            preparedStatement.setString(7, level);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding question: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewQuestions() {
        String query = "SELECT * FROM questions";
        StringBuilder questionsText = new StringBuilder();

        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                questionsText.append("ID: ").append(resultSet.getInt("id")).append("\n");
                questionsText.append("Question: ").append(resultSet.getString("question")).append("\n");
                questionsText.append("A: ").append(resultSet.getString("option_a")).append("\n");
                questionsText.append("B: ").append(resultSet.getString("option_b")).append("\n");
                questionsText.append("C: ").append(resultSet.getString("option_c")).append("\n");
                questionsText.append("D: ").append(resultSet.getString("option_d")).append("\n");
                questionsText.append("Correct: ").append(resultSet.getString("correct_answer")).append("\n\n");
            }
            questionTextArea.setText(questionsText.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching questions: " + e.getMessage());
        }
    }

    private void openUpdateQuestionDialog() {
        String questionId = JOptionPane.showInputDialog(this, "Enter Question ID to Update:");
        if (questionId == null || questionId.trim().isEmpty())
            return;

        String newQuestion = JOptionPane.showInputDialog(this, "Enter New Question:");
        if (newQuestion == null || newQuestion.trim().isEmpty())
            return;

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE questions SET question = ? WHERE id = ?")) {
            preparedStatement.setString(1, newQuestion);
            preparedStatement.setInt(2, Integer.parseInt(questionId));
            int updated = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, updated > 0 ? "Question Updated!" : "Question ID Not Found!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating question: " + e.getMessage());
        }
    }

    private void openDeleteQuestionDialog() {
        String questionId = JOptionPane.showInputDialog(this, "Enter Question ID to Delete:");
        if (questionId == null || questionId.trim().isEmpty())
            return;

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM questions WHERE id = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(questionId));
            int deleted = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, deleted > 0 ? "Question Deleted!" : "Question ID Not Found!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting question: " + e.getMessage());
        }
    }

    private void viewReports() {
        // Fetch user data from the database
        String query = "SELECT id, name, age, score, level FROM users ORDER BY score DESC";
        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            // Create a table model to hold the data
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Age");
            tableModel.addColumn("Score");
            tableModel.addColumn("Level");

            // Add rows to the table model
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int score = resultSet.getInt("score");
                String level = resultSet.getString("level");
                tableModel.addRow(new Object[] { id, name, age, score, level != null ? level : "N/A" });
            }

            // Create a JTable with the table model
            JTable userTable = new JTable(tableModel);
            userTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            userTable.setRowHeight(40);
            userTable.setFillsViewportHeight(true);
            userTable.setSelectionBackground(StyleConstants.HOVER_COLOR);
            userTable.setSelectionForeground(Color.BLACK);
            userTable.setGridColor(StyleConstants.BORDER_COLOR);
            
            // Style table header
            userTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
            userTable.getTableHeader().setBackground(StyleConstants.PRIMARY_COLOR);
            userTable.getTableHeader().setForeground(Color.WHITE);
            userTable.getTableHeader().setPreferredSize(new Dimension(0, 50));
            userTable.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(userTable);
            scrollPane.setBorder(BorderFactory.createLineBorder(StyleConstants.BORDER_COLOR, 1));

            // Create a dialog to display the table
            JDialog reportDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "User Analytics", true);
            reportDialog.setLayout(new BorderLayout(0, 0));
            reportDialog.setSize(1000, 600);
            reportDialog.setLocationRelativeTo(this);
            
            // Header
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(StyleConstants.PRIMARY_COLOR);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
            JLabel headerLabel = new JLabel("User Analytics & Performance");
            headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
            headerLabel.setForeground(Color.WHITE);
            headerPanel.add(headerLabel);
            reportDialog.add(headerPanel, BorderLayout.NORTH);
            
            // Table panel
            JPanel tablePanel = new JPanel(new BorderLayout());
            tablePanel.setBackground(Color.WHITE);
            tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            tablePanel.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(tablePanel, BorderLayout.CENTER);
            
            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
            buttonPanel.setBackground(Color.WHITE);
            JButton closeButton = new CustomButton("Close", StyleConstants.PRIMARY_COLOR, Color.WHITE, StyleConstants.PRIMARY_DARK);
            closeButton.addActionListener(e -> reportDialog.dispose());
            buttonPanel.add(closeButton);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);
            
            reportDialog.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching user reports: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        MainFrame.getInstance().showLogin();
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(60, 120, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
    }
}