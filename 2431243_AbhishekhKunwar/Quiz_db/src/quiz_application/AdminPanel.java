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
        adminLabel.setForeground(StyleConstants.ACCENT_COLOR);
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
        welcomeLabel.setForeground(StyleConstants.PRIMARY_COLOR);
        mainContent.add(welcomeLabel, BorderLayout.NORTH);

        questionTextArea = new JTextArea();
        questionTextArea.setEditable(false);
        questionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        questionTextArea.setBackground(Color.WHITE);
        questionTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
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
        StyleConstants.styleButton(btn);
        btn.setBackground(StyleConstants.SECONDARY_COLOR);
        btn.addActionListener(listener);
        parent.add(btn);
        parent.add(Box.createVerticalStrut(15));
    }

    private void openAddQuestionDialog() {
        JDialog addDialog = new JDialog(MainFrame.getInstance(), "Add Question", true);
        addDialog.setLayout(new GridLayout(7, 2, 10, 10));
        addDialog.setSize(400, 350);
        addDialog.setLocationRelativeTo(MainFrame.getInstance());

        JTextField questionField = new JTextField();
        JTextField optionAField = new JTextField();
        JTextField optionBField = new JTextField();
        JTextField optionCField = new JTextField();
        JTextField optionDField = new JTextField();
        JTextField correctAnswerField = new JTextField();

        addDialog.add(new JLabel("Question:"));
        addDialog.add(questionField);
        addDialog.add(new JLabel("Option A:"));
        addDialog.add(optionAField);
        addDialog.add(new JLabel("Option B:"));
        addDialog.add(optionBField);
        addDialog.add(new JLabel("Option C:"));
        addDialog.add(optionCField);
        addDialog.add(new JLabel("Option D:"));
        addDialog.add(optionDField);
        addDialog.add(new JLabel("Correct Answer (A/B/C/D):"));
        addDialog.add(correctAnswerField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            addQuestionToDatabase(questionField.getText(), optionAField.getText(), optionBField.getText(),
                    optionCField.getText(), optionDField.getText(), correctAnswerField.getText());
            addDialog.dispose();
        });
        addDialog.add(saveButton);
        addDialog.setVisible(true);
    }

    private void addQuestionToDatabase(String question, String optionA, String optionB, String optionC, String optionD,
            String correctAnswer) {
        String query = "INSERT INTO questions (question, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, question);
            preparedStatement.setString(2, optionA);
            preparedStatement.setString(3, optionB);
            preparedStatement.setString(4, optionC);
            preparedStatement.setString(5, optionD);
            preparedStatement.setString(6, correctAnswer);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Question added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding question: " + e.getMessage());
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
        // Fetch user data from the database, including the highest score and games
        // played
        String query = "SELECT id, name, MAX(score) AS highest_score, COUNT(*) AS games_played " +
                "FROM users " +
                "GROUP BY id, name";
        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            // Create a table model to hold the data
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Highest Score");
            tableModel.addColumn("Games Played");

            // Add rows to the table model
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int highestScore = resultSet.getInt("highest_score");
                int gamesPlayed = resultSet.getInt("games_played");
                tableModel.addRow(new Object[] { id, name, highestScore, gamesPlayed });
            }

            // Create a JTable with the table model
            JTable userTable = new JTable(tableModel);
            userTable.setFillsViewportHeight(true);

            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(userTable);

            // Create a dialog to display the table
            JDialog reportDialog = new JDialog(this, "User Reports", true);
            reportDialog.setLayout(new BorderLayout());
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.setSize(600, 400);
            reportDialog.setLocationRelativeTo(this);
            reportDialog.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching user reports: " + e.getMessage());
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